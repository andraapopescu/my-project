package application.demo.ui.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.demo.service.EmployeeSkillService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource.StreamSource;

import application.demo.domain.Employee;
import application.demo.domain.EmployeeSkill;

public class Pdf implements StreamSource {
	private static final long serialVersionUID = 1L;

	private ByteArrayOutputStream os = new ByteArrayOutputStream();
	SimpleDateFormat sdf = new SimpleDateFormat("E , d MMMMM yyyy ");

	public Pdf(Employee selectedEmployee) {
		Document document = null;

		try {
			document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter.getInstance(document, os);
			document.open();

			Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY);

			Image img;
			try {
				img = Image.getInstance("./src/main/resources/profil/" + selectedEmployee.getId());
			} catch (Exception E) {
				img = Image.getInstance("./src/main/resources/profil/default.png");
			}

			img.scaleAbsolute(100f, 100f);

			document.add(new Paragraph(" Curriculum Vitae ", titleFont));
//			document.add(new Paragraph(
//					" CV: " + selectedEmployee.getFirstName().substring(0, 3)
//							+ selectedEmployee.getLastName().substring(0, 3) + selectedEmployee.getPhone(),
//					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLDITALIC, BaseColor.BLACK)));
			document.add(new Paragraph(""));
			document.add(new Paragraph("______________________________________________________________________"));

			document.add((Element) img);
			document.add(new Paragraph("______________________________________________________________________"));

			document.add(new Paragraph("First Name:  ",
					new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK)));
			document.add(new Paragraph(selectedEmployee.getFirstName()));
			document.add(
					new Paragraph("Last Name:  ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK)));
			document.add(new Paragraph(selectedEmployee.getLastName()));
			document.add(new Paragraph("Birthday:  ",
					new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK)));
			document.add(new Paragraph(sdf.format(selectedEmployee.getBirthday())));
			
			document.add(new Paragraph(""));
			document.add(new Paragraph("______________________________________________________________________"));
			document.add(new Paragraph("Contact Info:  ",
					new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK)));
			document.add(new Paragraph(""));
			document.add(new Paragraph("  Phone: " + selectedEmployee.getPhone()));
			document.add(new Paragraph("  Email: " + selectedEmployee.getEmail()));
			document.add(new Paragraph("______________________________________________________________________"));

			if (selectedEmployee.getAddress().length() != 0) {
				document.add(
						new Paragraph("Adress: ", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK)));
				document.add(new Paragraph(selectedEmployee.getAddress()));
				document.add(new Paragraph("______________________________________________________________________"));
			}

			document.add(new Paragraph("Skills:",
					new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, BaseColor.BLACK)));
			document.add(new Paragraph(""));
			document.add(new Paragraph("\n"));
			ArrayList<EmployeeSkill> emSkills = EmployeeSkillService.getEmployeeSkillByEmployee(selectedEmployee.getId());

			if (emSkills != null)
				for (EmployeeSkill es : emSkills) {
					int level = es.getLevel();

					String skillName = es.getSkill().getName();

					if (level > 0) {
						if (level < 3) {
							document.add(new Paragraph("      " + skillName.toUpperCase() + "   -   " + " Junior Level "));
						}
						if (level >= 3 && level < 6) {
							document.add(new Paragraph("      " +skillName.toUpperCase() + "   -   " + " Medium Level "));
						}
						if (level >= 6) {
							document.add(new Paragraph("      " +skillName.toUpperCase() + "   -   " + " Advanced Level "));
						}
					}
				}
			document.add(new Paragraph("______________________________________________________________________"));

			document.add(new Paragraph(""));
			document.add(new Paragraph("Document's date: " + sdf.format(new Date()), new Font(Font.FontFamily.HELVETICA, 14, 
					Font.ITALIC, BaseColor.BLACK)));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	@Override
	public InputStream getStream() {
		// Here we return the pdf contents as a byte-array
		return new ByteArrayInputStream(os.toByteArray());
	}
}