package fr.eyal.datalib.sample.netflix.data.model.movieimage;

import java.lang.ref.SoftReference;

import android.content.Context;
import android.content.OperationApplicationException;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.lib.util.FileManager;



public class MovieImageBase implements ResponseBusinessObject {

    private static final String TAG = MovieImageBase.class.getSimpleName();

    private static String CACHE_DIRECTORY = "movieimage";

	public SoftReference<Bitmap> image;
	public String imagePath;
	
    public MovieImageBase() {
        super();
    }

    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<MovieImageBase> CREATOR = new Parcelable.Creator<MovieImageBase>() {
	    @Override
	    public MovieImageBase createFromParcel(final Parcel in) {
	        return new MovieImageBase(in);
	    }
	
	    @Override
	    public MovieImageBase[] newArray(final int size) {
	        return new MovieImageBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeParcelable(image.get(), flags);
	}

	public MovieImageBase(final Parcel in) {
		image = new SoftReference<Bitmap>((Bitmap) in.readParcelable(Bitmap.class.getClassLoader()));
	}    

    @Override
    public void save(final DataLibRequest request) throws RemoteException, OperationApplicationException {
		String name = request.getRequestFileName();
    	String extension = FileManager.getFileExtension(name);
    	
    	if(name.lastIndexOf(extension) != name.length() - extension.length())
    		name = name.concat(extension);
    	Context context = request.context;
    	imagePath = FileManager.saveInInternalCache(context, CACHE_DIRECTORY, name, image.get(), 100);
    }

}

