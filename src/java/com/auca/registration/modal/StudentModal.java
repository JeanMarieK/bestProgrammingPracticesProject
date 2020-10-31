package com.auca.registration.modal;

import com.auca.registration.domain.*;
import com.auca.registration.dao.*;
import com.auca.registration.sessionListener.SessionListner;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.omnifaces.cdi.GraphicImageBean;
import org.primefaces.model.UploadedFile;

@GraphicImageBean
@ManagedBean(name = "sm")
@SessionScoped
public class StudentModal implements Serializable {
    private static HttpSession session;
    private String res;
    private double income;
    private String firstName;
    private String lastName;
    private String nid;
    private Date dob;
    private Gender gender;
    private String disability;
    private String village;
    private String cell;
    private String sector;
    private String district;
    private String province;
    private String email;
    private String phoneNumber;
    private UploadedFile photo;
    private UploadedFile pic;
    private String studentId;
    private int department;
    private int specialization;
    private int program;
    private int shift;
    private int paymentMode;
    private String motherName;
    private String motherNumber;
    private String fatherName;
    private String fatherNumber;
    private String modeName;
    private String modeDescription;

    private int faculty;
    @Inject
    private AddressDao addDao ;
    @Inject
    private StudentDao stDao;
    @Inject
    private DepartmentDao deptDao;
    String msg = "";
    List<String> prov;
    List<String> dist;
    List<String> sec;
    List<String> cel;
    List<String> vil;

    public List<String> aprovinces() {
        prov = addDao.provinces();
        dist = null;
        sec = null;
        cel = null;
        vil = null;
        return prov;
    }

    public List<String> adistricts(String n) {
        dist = addDao.districts(n);
        sec = null;
        cel = null;
        vil = null;
        return dist;
    }

    public List<String> asectors(String n) {
        sec = addDao.sectors(n);
        cel = null;
        vil = null;
        return sec;
    }

    public List<String> acells(String n) {
        cel = addDao.cells(n);
        vil = null;
        return cel;
    }

    public List<String> avillages(String n) {
        vil = addDao.villages(n);
        return vil;
    }

    public List<Student> pendingStudents() {
        return stDao.allPendingStudents();
    }

    public List<Student> approvedStudents() {
        return stDao.allApprovedStudents();
    }

    public List<Student> rejectedStudents() {
        return stDao.allRejectedStudents();
    }

    public void approve(int n, String number) {
        stDao.approve(n);
//        stDao.testSample("0" + number, "Congratulations! your registration has been approved by the registra. Your new Student ID is: " + n);
        
    }

    public void reject(int n, String number) {
        stDao.reject(n);
        stDao.testSample("0" + number, "Sorry! your registration has been rejected by the registra. Kindly try again next semester.");

    }

    public byte[] picture(int n) throws ClassNotFoundException {
        return stDao.retrieve(n);
    }

    public void saveStudent() {
        if (nid.length() == 16) {
            if (nid.startsWith("1")) {
//                if (nid.substring(1, 4).equals(String.valueOf(dob.getYear()))) {
                    if (Long.parseLong(nid) != 0) {
                        if (phoneNumber.startsWith("7")) {
                            if (phoneNumber.length() == 9) {
                                if (Long.parseLong(phoneNumber) != 0) {
                                    try {
                                        Student st = new Student();
                                        Location loc = new Location();
                                        st.setFirstName(firstName);
                                        st.setLastName(lastName);
                                        st.setNid(nid);
                                        st.setDob(new Date());
                                        st.setGender(gender.toString());
                                        st.setDisability(disability);
                                        st.setEmail(email);
                                        st.setPhoneNumber(phoneNumber);
                                        st.setDepartment(department);
                                        st.setMotherName(motherName);
                                        st.setMotherNumber(motherNumber);
                                        st.setFatherName(fatherName);
                                        st.setFatherNumber(fatherNumber);
                                        loc.setProvince(province);
                                        loc.setDistrict(district);
                                        loc.setSector(sector);
                                        loc.setCell(cell);
                                        loc.setVillage(village);
                                        stDao.saveLocation(loc);

                                        int location_id = stDao.idMax("location");
                                        st.setLocation(location_id);
                                        st.setResidence(res);
                                        st.setIncome(income);
                                        stDao.save(st);
                                        System.out.println("FINISHED SAVING ST.......");
                                        try {
                                            stDao.savePic(pic);
                                        } catch (Exception e) {
                                            System.out.println("ERROR ON PIC MODAL: " + e);
                                            System.out.println("TIS IS THE STACK TRACE\n\n\n");
                                            e.printStackTrace();
                                        }
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The student was saved successfully"));
                                    } catch (Exception e) {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "An error was encountered during execution of the save student request" + e));
                                    }
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Validation Error!", "The phone number should be composed of digots only!"));
                                }
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error!", "The phone number should start with 7"));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error!", "The National ID should be composed of digits only"));
                    }
//                } else {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error!", "The National ID's year of birth should match the provided date of birth"));
//                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error!", "The National ID should start with digit 1"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error!", "The National ID must have 16 digits"));
        }
    }
    public int numberOfAllStudents(){
        return stDao.studentsCount();
    }
    public int numberOfAllRejectedStudents(){
        return stDao.studentsCountRejected();
    }
    public int numberOfAllApprovedStudents(){
        return stDao.studentsCountApproved();
    }

    public List<Department> findDepartmentByFacultyId() {
        return deptDao.findDepartmentByFacultyId(faculty);
    }

    public int studentID() {
        return stDao.idMaxStudent("student") + 1;
    }
    public int facultyID() {
        return stDao.idMaxStudent("faculty") + 1;
    }
    public int departmentID() {
        return stDao.idMaxStudent("department") + 1;
    }
    
    public int sessionCount(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return new SessionListner().getCounter();
    }

    public UploadedFile getPic() {
        return pic;
    }

    public void setPic(UploadedFile pic) {
        this.pic = pic;
    }

    public StudentDao getStDao() {
        return stDao;
    }

    public void setStDao(StudentDao stDao) {
        this.stDao = stDao;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getFaculty() {
        return faculty;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getVillage() {
        return village;
    }

    public List<String> getProv() {
        return prov;
    }

    public void setProv(List<String> prov) {
        this.prov = prov;
    }

    public List<String> getDist() {
        return dist;
    }

    public void setDist(List<String> dist) {
        this.dist = dist;
    }

    public List<String> getSec() {
        return sec;
    }

    public void setSec(List<String> sec) {
        this.sec = sec;
    }

    public List<String> getCel() {
        return cel;
    }

    public void setCel(List<String> cel) {
        this.cel = cel;
    }

    public List<String> getVil() {
        return vil;
    }

    public void setVil(List<String> vil) {
        this.vil = vil;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public static HttpSession getSession() {
        return session;
    }

    public static void setSession(HttpSession session) {
        StudentModal.session = session;
    }

    public String getModeDescription() {
        return modeDescription;
    }

    public void setModeDescription(String modeDescription) {
        this.modeDescription = modeDescription;
    }

    public UploadedFile getPhoto() {
        return photo;
    }

    public void setPhoto(UploadedFile photo) {
        this.photo = photo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getSpecialization() {
        return specialization;
    }

    public void setSpecialization(int specialization) {
        this.specialization = specialization;
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherNumber() {
        return motherNumber;
    }

    public void setMotherNumber(String motherNumber) {
        this.motherNumber = motherNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherNumber() {
        return fatherNumber;
    }

    public void setFatherNumber(String fatherNumber) {
        this.fatherNumber = fatherNumber;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
    
}
