import java.util.Scanner;
import javax.swing.JOptionPane;

public class RectangleArea {

	public static Scanner input;

	public static void main(String[] args) {

		input = new Scanner(System.in);
		double area;

		Scanner in = null;
		int Length = Integer.parseInt(getInput(in, "Enter the length of the Rectangle. "));
		int Width = Integer.parseInt(getInput(in, "Enter the Width of the Rectangle. "));
		area = Length * Width;
		JOptionPane.showMessageDialog(null, " The area of the Rectangle is  " + area );
	}


 public static String getInput (Scanner in, String prompt){
	 JOptionPane.showMessageDialog(null, prompt);
	 String text = " ";
	 while (true){
		 
		 text = in.nextLine();
		 if (isInteger(text))
			 break;
		 JOptionPane.showMessageDialog(null, "Try Again, " + prompt);
		 
		 
	 }
	return text;
	 
 }

private static boolean isInteger (String str) {
	try {
		Integer.parseInt(str);
		return true;
	} catch (NumberFormatException e){
	return false;
}
	
}

}
	
