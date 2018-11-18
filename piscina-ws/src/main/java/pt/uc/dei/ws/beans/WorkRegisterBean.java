package pt.uc.dei.ws.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import pt.piscina.itf.dtos.TaskworkNewDto;
import pt.piscina.itf.dtos.WorkRegisterDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.TaskWorkBridge;
import pt.uc.dei.ws.bridges.UserBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("workRegisterBean")
@SessionScoped
public class WorkRegisterBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<WorkRegisterDto> workRegisterList;
	private WorkRegisterDto selectedWorkRegister;
	private int allocationId;
	private String comments;
	private int hoursWorked;
	private int remainingHours;
	private int expectedRemainingHours;
	private byte[] document;
	private String fileName;
	private Part file1=null;
	private Part file2=null;
	private Part file3=null;
	private Part file4=null;
	private Part file5=null;
	private List<byte[]> documents=new ArrayList<byte[]>();
	private List<String> documentsNames=new ArrayList<String>();

	@Inject
	private UserBridge userBridge;

	@Inject
	private TaskWorkBridge taskWorkBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;

	public WorkRegisterBean() {
	}

	public String upload(Part file) throws IOException{
		if (file==null) {
			return "No file selected";
		}
		InputStream input = file.getInputStream();	
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];//TODO Qual o size maximo da PHOTO????
		for (int length = 0; (length = input.read(buffer)) > 0;) {
			output.write(buffer, 0, length);
		}
		fileName = file.getSubmittedFileName();
		document = output.toByteArray();
		documents.add(document);
		documentsNames.add(fileName);
		return "File uploaded";
	}
	
	//TODO mudar posteriormente para upload multiple files
	public void uploadFiles() throws IOException{	
		if (file1!=null) {
			upload(file1);
		}
		if (file2!=null) {
			upload(file2);
		}
		if (file3!=null) {
			upload(file3);
		}
		if (file4!=null) {
			upload(file4);
		}
		if (file5!=null) {
			upload(file5);
		}
	}


	public void registerWorkingHoursInTaskFirstStep() {
	}

	public String registerWorkingHoursInTask() {
		allocationId=selectedWorkRegister.getAllocationId();
		expectedRemainingHours = selectedWorkRegister.getHoursToFinish()-hoursWorked;
		try {
			uploadFiles();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		TaskworkNewDto taskworkNewDto = new TaskworkNewDto(allocationId, comments,hoursWorked, remainingHours, expectedRemainingHours, documents, documentsNames);
		Response response = taskWorkBridge.registerWorkingHoursInTask(taskworkNewDto);
		if (response.getStatus()==200) {
			setFieldsToNull();
			
			errorsHandler.registerWorkingHoursInTask(true, null);
			return null;
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.registerWorkingHoursInTask(false, errors);
			setFieldsToNull();
			return null;
		}
	}

	private void setFieldsToNull() {
		documents=new ArrayList<byte[]>();
		documentsNames=new ArrayList<String>();
		fileName=null;
		file1=null;
		file2=null;
		file3=null;
		file4=null;
		file5=null;
		comments="";
		hoursWorked=0;
		remainingHours=0;
		expectedRemainingHours=0;
	}
	
	public void cleanFields() {
		documents=new ArrayList<byte[]>();
		documentsNames=new ArrayList<String>();
		fileName=null;
		file1=null;
		file2=null;
		file3=null;
		file4=null;
		file5=null;
		comments="";
		hoursWorked=0;
		remainingHours=0;
		expectedRemainingHours=0;
	}

	//public List<WorkRegisterDto> findWorkRegisterList() {
	public void findWorkRegisterList() {
		Response response = userBridge.getWorkRegisterList();
		if (response.getStatus()==200) {
			workRegisterList=response.readEntity(new GenericType<List<WorkRegisterDto>>() {});
			if (workRegisterList==null){
				System.out.println("NULL");
			}
				
			//return workRegisterList;
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
		}
	}


	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<WorkRegisterDto> getWorkRegisterList() {
		return workRegisterList;
	}

	public void setWorkRegisterList(List<WorkRegisterDto> workRegisterList) {
		this.workRegisterList = workRegisterList;
	}

	public WorkRegisterDto getSelectedWorkRegister() {
		return selectedWorkRegister;
	}

	public void setSelectedWorkRegister(WorkRegisterDto selectedWorkRegister) {
		this.selectedWorkRegister = selectedWorkRegister;
	}

	public int getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(int allocationId) {
		this.allocationId = allocationId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public int getRemainingHours() {
		return remainingHours;
	}

	public void setRemainingHours(int remainingHours) {
		this.remainingHours = remainingHours;
	}

	public int getExpectedRemainingHours() {
		return expectedRemainingHours;
	}

	public void setExpectedRemainingHours(int expectedRemainingHours) {
		this.expectedRemainingHours = expectedRemainingHours;
	}

	public List<byte[]> getDocuments() {
		return documents;
	}

	public void setDocuments(List<byte[]> documents) {
		this.documents = documents;
	}

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public Part getFile1() {
		return file1;
	}

	public void setFile1(Part file1) {
		this.file1 = file1;
	}

	public Part getFile2() {
		return file2;
	}

	public void setFile2(Part file2) {
		this.file2 = file2;
	}

	public Part getFile3() {
		return file3;
	}

	public void setFile3(Part file3) {
		this.file3 = file3;
	}

	public Part getFile4() {
		return file4;
	}

	public void setFile4(Part file4) {
		this.file4 = file4;
	}

	public Part getFile5() {
		return file5;
	}

	public void setFile5(Part file5) {
		this.file5 = file5;
	}

}
