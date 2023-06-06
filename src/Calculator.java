import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends Frame {
    public boolean setClear = true;
    double number,memValue;
    char Operation;

    String digitButtonText[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", "." };
    String operatoButton[] = {"/", "sqrt", "*", "%", "-", "1/X", "+", "=" };
    String specialButtonText[] = {"Backspc", "C", "CE" };

    DigitButton digitButton[] = new DigitButton[digitButtonText.length];

    Label displayLabel = new Label("0",Label.RIGHT);
    Label memLabel = new Label(" ",Label.RIGHT);

    final int FRAME_WIDTH=325,FRAME_HEIGHT=325;
    final int HEIGHT=30, WIDTH=30, H_SPACE=10,V_SPACE=10;
    final int TOPX=30, TOPY=50;

    Calculator(String frameText){
        super(frameText);

        int tempX = TOPX,y = TOPY;
        displayLabel.setBounds(tempX,y,320,HEIGHT);
        displayLabel.setBackground(Color.WHITE);
        displayLabel.setBackground(Color.BLACK);
        add(displayLabel);

        memLabel.setBounds(TOPX,  TOPY+HEIGHT+ V_SPACE,WIDTH, HEIGHT);
        add(memLabel);

        //    set Co-ordinate for Digit-Button
        int digitX = TOPX+WIDTH+H_SPACE;
        int digitY = TOPY+2*(HEIGHT+V_SPACE);
        tempX = digitX;
        y=digitY;
        for(int i=0;i<digitButton.length;i++){
            digitButton[i] = new DigitButton(tempX,y,WIDTH,HEIGHT,digitButtonText[i],this);
            digitButton[i].setForeground(Color.YELLOW);
            tempX+=WIDTH+H_SPACE;
            if((i+1)%3==0){
                tempX = digitX;
                y+=HEIGHT+V_SPACE;
            }
        }
    }

    public static void main(String[] args) {
        new Calculator("Calcutor");
    }

}

class DigitButton extends Button implements ActionListener{
    Calculator cl;

    DigitButton(int x,int y,int width,int height,String cap,Calculator clc){
        super(cap);
        setBounds(x,y,width,height);
        this.cl = clc;
        this.cl.add(this);
        addActionListener(this);
    }

    static boolean isInString(String s, char ch)
    {
        for(int i=0; i<s.length();i++) if(s.charAt(i)==ch) return true;
        return false;
    }

    public void actionPerformed(ActionEvent ev){
        String tempText = ((DigitButton)ev.getSource()).getLabel();

        if(tempText.equals(".")){
            if(cl.setClear){
                cl.displayLabel.setText("0.");
                cl.setClear=false;
            }
            else if(!isInString(cl.displayLabel.getText(),'.')){
                cl.displayLabel.setText(cl.displayLabel.getText() + ".");
            }
            return;
        }
        int index = 0;
        try {
            index = Integer.parseInt(tempText);
        } catch (NumberFormatException e){
            return;
        }

        if(cl.setClear){
            cl.displayLabel.setText(""+index);
            cl.setClear=false;
        }
        else{
            cl.displayLabel.setText(cl.displayLabel.getText()+index);
        }
    }
}
