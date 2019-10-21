package vn.com.mob1032_assignment104.DataModel;

public class Students {
    int studentID;
    String studentName;
    String classID;

    public Students(){}
    public Students(int studentID, String studentName, String classID) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.classID = classID;
    }

    public Students(String studentName, String classID) {
        this.studentName = studentName;
        this.classID = classID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
