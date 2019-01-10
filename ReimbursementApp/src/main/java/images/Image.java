package images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Image {
	private int itemID;
	private String imageName;
	InputStream fileContent;

	public Image(String imageName, InputStream fileContent) {
		super();
		this.itemID = -1;
		this.imageName = imageName;
		this.fileContent = fileContent;
	}
	public Image(int itemID, String imageName, InputStream fileContent) {
		super();
		this.itemID = itemID;
		this.imageName = imageName;
		this.fileContent = fileContent;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
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

	public void uploadImageLocally() {
		String filePath = "C:\\Users\\Associate\\java\\project-one-Rakatashii\\ReimbursementApp\\src\\main\\webapp\\uploads\\" 
				+ this.imageName.replaceAll("[ ]+", "");
		File imageFile = new File(filePath);
		System.out.println(imageFile.getAbsolutePath());
		if (imageFile.exists() == false) {
			try {
				imageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace(); System.out.println();
			}
		}
		if (imageFile.exists()) {
			System.out.println("imageFile Path: " + imageFile.getAbsolutePath());
			System.out.println("Image File Exists.");
			try {
				byte[] buffer = new byte[fileContent.available()];
				fileContent.read(buffer);
				OutputStream os = new FileOutputStream(imageFile);
			    os.write(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Image File Still Does Not Exist.");
		}
	}
}
