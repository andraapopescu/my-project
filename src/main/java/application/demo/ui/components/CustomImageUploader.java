package application.demo.ui.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.vaadin.server.FileResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class CustomImageUploader extends CustomComponent {

	Image image;
	Panel panel;
	VerticalLayout panelContent;
	FileOutputStream fos = null;
	public File file;
	boolean uploaded = false;
	String imageType;

	// Implement both receiver that saves upload in a file and
	// listener for successful upload
	class ImageReceiver implements Receiver, SucceededListener {

		public OutputStream receiveUpload(String filename, String mimeType) {
			// Create upload stream
			// Stream to write to

			String type[] = mimeType.split("[//]");
			imageType = type[type.length - 1];

			try {
				// Open the file for writing.
				file = new File("./src/main/resources/images/" + filename);
				fos = new FileOutputStream(file);

			} catch (final java.io.FileNotFoundException e) {
				return null;
			}
			return fos; // Return the output stream to write to
		}

		public void uploadSucceeded(SucceededEvent event) {
			// Show the uploaded file in the image viewer
			image.setVisible(true);
			// image.setSource(new FileResource(file));
			panelContent.removeComponent(image);
			image = new Image();
			image.setWidth("170px");
			image.setHeight("200px");

			image.setSource(new FileResource(file));
			panelContent.addComponent(image);
			uploaded = true;
		}
	};

	public CustomImageUploader(String imgPath) {
		FileResource resource;
		File user = new File(imgPath);
		if (user.exists()) {
			resource = new FileResource(user);

		} else {
			File file = new File("./src/main/resources/profil/default.png");
			resource = new FileResource(file);

		}

		image = new Image(null, resource);
		image.setWidth("170px");
		image.setHeight("200px");

		final long UPLOAD_LIMIT = 1000000l;
		ImageReceiver receiver = new ImageReceiver();

		final Upload upload = new Upload(null, receiver);
		// Create the upload with a caption and set receiver later

		upload.addSucceededListener(receiver);
		upload.setButtonCaption("Choose image");
		upload.setImmediate(true);

		upload.addProgressListener(new ProgressListener() {
			@Override
			public void updateProgress(long readBytes, long contentLength) {
				if (readBytes > UPLOAD_LIMIT) {
					upload.interruptUpload();
					uploaded = false;
				}
			}
		});

		upload.addStartedListener(new StartedListener() {

			public void uploadStarted(StartedEvent event) {

				if (event.getContentLength() > UPLOAD_LIMIT) {
					upload.interruptUpload();
					uploaded = false;
				}
			}
		});

		panelContent = new VerticalLayout();
		panelContent.setMargin(true);
		panelContent.addComponents(upload, image);

		setCompositionRoot(panelContent);
	}

	public void save(long employeeId) {

		if (new File("./src/main/resources/profil/" + employeeId).exists()) {
			new File("./src/main/resources/profil/" + employeeId).delete();

		}
		file.renameTo(new File("./src/main/resources/profil/" + employeeId));
		uploaded = false;
		try {
			FileUtils.cleanDirectory(new File("./src/main/resources/images/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void deleteImage() {
		file.delete();
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}
}
