//Factory Design Pattern
//Create an onlineClasses app
//Attributes: name of the teacher
//            gender of the teacher
//            activity (On/Off)
//Methods:  getSalutation() -> "Mr" or "Mrs" according to the gender
//          getName() -> returns the name
//          getActivity() -> "On" or "off"

public class OnlineClasses {
    public static class TeacherFactory {
        public static Teacher getTeacher(String name, String gender, String activity) {
            if(gender.equalsIgnoreCase("M")) {
                    return new Male(name, activity);
            } else if(gender.equalsIgnoreCase("F")) {
                return new Female(name, activity);
            } // So on
            return null;
        }
    }
static abstract class Teacher {
    public Teacher(String name, String activity) {
        this.name = name;
        this.activity = activity;
    }
    private String name;
    private String activity;

    abstract String getSalutation();
    String getActivity(){
        return this.activity;
    }
    String getName() {
        return this.name;
    }
}

static class Male extends Teacher {
    public Male(String name, String activity) {
        super(name, activity);
    }
    @Override
    String getSalutation() {
        return "Mr";
    }

}
static class Female extends Teacher {
    public Female(String name, String activity) {
        super(name, activity);
    }
    @Override
    String getSalutation() {
        return "Miss/Mrs";
    }
}

public static void main(String[] args) {
        Teacher male = TeacherFactory.getTeacher("Robinhood", "M", "On");
        System.out.println(male.getSalutation() + " " + male.getName() + " is " + male.getActivity());
        Teacher female = TeacherFactory.getTeacher("Mary", "F", "OFF");
        System.out.println(female.getSalutation() + " " + female.getName() + " is " + female.getActivity());
        }
}