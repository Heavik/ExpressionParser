
public class Main {

    public static void main(String[] args) {

        Parser pars = new Parser();

        String str1 = "2*3";
        System.out.println(str1 + " = " + pars.parse(str1));
        String str2 = "12+(5/2)";
        System.out.println(str2 + " = " + pars.parse(str2));
        String str3 = "((5/2)*2)+5";
        System.out.println(str3 + " = " + pars.parse(str3));
        String str4 = "((5/2)*(2+(1*2)))+5";
        System.out.println(str4 + " = " + pars.parse(str4));
    }
}
