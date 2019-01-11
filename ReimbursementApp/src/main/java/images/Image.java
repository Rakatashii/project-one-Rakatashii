package images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Image {
	public static final String UPLOAD_DIRECTORY = "C:\\Users\\Associate\\java\\project-one-Rakatashii\\ReimbursementApp\\src\\main\\webapp\\uploads\\";
	private int imageID;
	private String imageName;
	
	InputStream fileContent;
	private boolean fileNotEmpty;
	File imageFile;
	int imageSize;
	byte[] bytestream;

	public Image(String imageName, InputStream fileContent) {
		super();
		this.imageID = -1;
		this.imageName = imageName;
		this.fileContent = fileContent;
		fileNotEmpty = false;
		imageFile = null;
		imageSize = -1;
		bytestream = null;
	}

	public Image(int imageID, String imageName, InputStream fileContent) {
		super();
		this.imageID = imageID;
		this.imageName = imageName;
		this.fileContent = fileContent;
		fileNotEmpty = false;
		imageFile = null;
		imageSize = -1;
		bytestream = null;
	}

	@Override
	public String toString() {
		return "Image [imageID=" + imageID + ", fileNotEmpty=" + fileNotEmpty + ", \n"
				+ "\t" + "imageName=" + (imageName != null ? imageName + ", " : "null")
				+ "imageFile=" + (imageFile != null ? imageFile + ", " : "null") 
				+ "imageSize=" + imageSize + "]";
	}
	
	public byte[] getBytestream() {
		return bytestream;
	}
	public void setBytestream(byte[] bytestream) {
		this.bytestream = bytestream;
	}
	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getFileContent() {
		return fileContent;
	}

	public void setFileContent(InputStream fileContent) {
		this.fileContent = fileContent;
	}
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	public void uploadImageLocally() {
		String filePath = UPLOAD_DIRECTORY + this.imageName.replaceAll("[ ]+", "");
		imageFile = new File(filePath);
		//System.out.println(imageFile.getAbsolutePath());
		if (imageFile.exists() == false) {
			try {
				imageFile.createNewFile();
				System.out.println("Image File Did Not Exist.\nImage File Was Created.");
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		} else {
			System.out.println("Image File Already Exists.");
		}
		if (imageFile.exists()) {
			System.out.println("- Path: " + imageFile.getAbsolutePath());
			try {
				imageSize = fileContent.available();
				bytestream = new byte[imageSize];
				fileContent.read(bytestream);
				OutputStream os = new FileOutputStream(imageFile);
			    os.write(bytestream);
			    fileNotEmpty = true;
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		} 
	}
	public boolean hasImageFile() {
		return (this.fileNotEmpty) ? true : false;
	}
	public File getImageFile() {
		return (hasImageFile()) ? imageFile : null;
	}
}
