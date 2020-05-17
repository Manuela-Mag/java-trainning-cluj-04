//Task:Choose 2 design patterns and implement them
//The first one is Builder, used for creating a student with 4 attributes


public class Person{
    static class Student {
        private Student(Builder builder) {
            this.level = builder.level;
            this.gendre = builder.gendre; //true for girl, false otherwise
            this.religion = builder.religion;
            this.nationality = builder.nationality;
        }
        private String level;
        private boolean gendre;
        private String religion;
        private String nationality;
        public static class Builder {
            private String level;
            private boolean gendre;
            private String religion;
            private String nationality;
            public Builder(String level) {
                this.level = level;
            }
            public Builder gendre(boolean value) {
                this.gendre = value;
                return this;
            }
            public Builder religion(String string) {
                this.religion = string;
                return this;
            }
            public Builder nationality(String value) {
                this.nationality = value;
                return this;
            }
            public Student build() {

                return new Student(this);

            }
        }
        @Override
        public String toString() {
            return String.format("Student \nLevel=%s\nGendre=%s\nReligion=%s\nNationality=%s\n", this.level, gendre, religion, nationality);

        }
    }

    public static void main(String[] args) {
        Student student = new Person.Student.Builder("Doctor").religion("Ortodox").gendre(false).nationality("British").build();
        System.out.println(student);
        //print student with his/hers attributes
    }

}
