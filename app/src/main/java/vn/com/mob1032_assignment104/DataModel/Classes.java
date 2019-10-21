package vn.com.mob1032_assignment104.DataModel;

public class Classes {
    String classID;
    String className;
    public Classes(){}
    public Classes(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
