package images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Image {
	public static final String UPLOAD_DIRECTORY = "C:\\Users\\Associate\\java\\project-one-Rakatashii\\ReimbursementApp\\src\\main\\webapp\\uploads\\";
	private int employeeID, reimbursementID;
	private String imageName;
	
	InputStream imageInput;
	OutputStream imageOutput;
	
	private boolean fileNotEmpty;
	File imageFile;
	int imageSize;
	byte[] bytestream;

	public Image(String imageName, InputStream imageInput) {
		super();
		this.employeeID = -1;
		this.reimbursementID = -1;
		this.imageName = imageName;
		this.imageInput = imageInput;
		if (imageSize == 0){
			try {			
				imageSize = imageInput.available();
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		}
		if (imageSize > 0) {
			fileNotEmpty = true;
			setFieldsWithInputStream();
		}
	}
	public Image(String imageName, File imageFile, int imageSize) {
		super();
		this.employeeID = -1;
		this.reimbursementID = -1;
		this.imageName = imageName;
		this.imageFile = imageFile;
		this.imageSize = imageSize;
		if (imageSize > 0) {
			fileNotEmpty = true;
			//uploadLocalFile();
		}
	}
	public Image(int employeeID, int reimbursementID, String imageName, InputStream imageInput) {
		super();
		this.employeeID = employeeID;
		this.reimbursementID = reimbursementID;
		this.imageName = imageName;
		this.imageInput = imageInput;
		if (imageSize == 0){
			try {			
				this.imageSize = imageInput.available();
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		}
		if (this.imageSize > 0) {
			fileNotEmpty = true;
			setFieldsWithInputStream();
		}
	}
	public Image(int employeeID, int reimbursementID, String imageName, File imageFile, int imageSize) {
		super();
		this.employeeID = employeeID;
		this.reimbursementID = reimbursementID;
		this.imageName = imageName;
		this.imageFile = imageFile;
		this.imageSize = imageSize;
		if (this.imageSize > 0) {
			fileNotEmpty = true;
			//uploadLocalFile();
		}
	}

	@Override
	public String toString() {
		return "Image [\nemployeeID = " + employeeID + ", reimbursementID=" + reimbursementID 
				+ ", fileNotEmpty=" + fileNotEmpty + ", \n"
				+ "\t" + "imageName=" + (imageName != null ? imageName + ", \n" : "null\n")
				+ "\t" + "imageFile=" + (imageFile != null ? imageFile + ", \n" : "null\n") 
				+ "\t" + "imageSize=" + imageSize 
				+ ", bytestream size = " 
				+ (bytestream != null && Arrays.toString(bytestream) != null ? Arrays.toString(bytestream).length() : "null")
				+ "\n]";
	}
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public byte[] getBytestream() {
		return bytestream;
	}
	public void setBytestream(byte[] bytestream) {
		this.bytestream = bytestream;
	}
	public int getReimbursementID() {
		return reimbursementID;
	}

	public void setReimbursementID(int reimbursementID) {
		this.reimbursementID = reimbursementID;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getInputStream() {
		return imageInput;
	}

	public void setInputStream(InputStream imageInput) {
		this.imageInput = imageInput;
	}
	public OutputStream getOutputStream() {
		return imageOutput;
	}

	public void setOutputStream(OutputStream imageOutput) {
		this.imageOutput = imageOutput;
	}
	
	public boolean hasImageFile() {
		return (this.fileNotEmpty) ? true : false;
	}
	public File getImageFile() {
		return (hasImageFile()) ? imageFile : null;
	}
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	public void uploadLocalFile() {
		if (!imageFile.exists() || imageFile.isDirectory()) {
			try {
				imageFile.createNewFile();
				System.out.println("Image File Was Created.");
			} catch (IOException e) {
				System.out.println("Unable To Create New File With Path Specified: " + imageName);
				e.printStackTrace(); System.out.println();
			}
		} else {
			System.out.println("Image File Already Exists.");
		} 
	}
	public void setFieldsWithInputStream() {
		if (imageName == null || imageName.length() == 0 || !imageName.contains(UPLOAD_DIRECTORY)) {
			imageName = UPLOAD_DIRECTORY + this.imageName.replaceAll("[ ]+", "");
			imageFile = new File(imageName);
		}
		if (imageFile != null) {
			try {
				if (imageSize == 0) {
					System.out.println("imageSize == 0 In #setFieldsWithInputStream!");
					imageSize = imageInput.available();
					System.out.println("\tNew imageSize = " + imageSize);
				}

				bytestream = new byte[imageSize];
				imageInput.read(bytestream);
				imageOutput = new FileOutputStream(imageFile);
			    imageOutput.write(bytestream);
			    fileNotEmpty = true;
			    
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		}
	}
	/*
	public void setFieldsWithImageFile() {
		if (imageFile != null) {
			System.out.println("--Path: " + imageFile.getAbsolutePath());
			if (imageSize > 0){
				try {
					if (bytestream.length == 0) {
						bytestream = new byte[imageSize];
						imageOutput = new FileOutputStream(imageFile);
					    imageOutput.write(bytestream);
					    fileNotEmpty = true;
					}
				} catch (IOException e) {
					e.printStackTrace(); System.out.println();
				}
			}
		}
	}
	*/
}
