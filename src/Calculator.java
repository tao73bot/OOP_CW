import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Calculator extends Frame {
    public boolean setClear = true;
    double number,memValue;
    char Operation;

    String digitButtonText[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", "." };
    String operationButtonText[] = {"/", "sqrt", "*", "%", "-", "1/X", "+", "=" };
    String specialButtonText[] = {"Backspc", "C"};

    DigitButton digitButton[] = new DigitButton[digitButtonText.length];
    OperationButton operationButton[] = new OperationButton[operationButtonText.length];
    SpecialButton specialButton[] = new SpecialButton[specialButtonText.length];

    Label displayLabel = new Label("0",Label.RIGHT);
    Label memLabel = new Label(" ",Label.RIGHT);

    final int FRAME_WIDTH=325,FRAME_HEIGHT=325;
    final int HEIGHT=30, WIDTH=30, H_SPACE=10,V_SPACE=10;
    final int TOPX=30, TOPY=50;

    Calculator(String frameText){
        super(frameText);

        int tempX = TOPX,y = TOPY;
        displayLabel.setBounds(tempX,y,240,HEIGHT);
        displayLabel.setBackground(Color.white);
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
            digitButton[i].setForeground(Color.BLACK);
            tempX+=WIDTH+H_SPACE;
            if((i+1)%3==0){
                tempX = digitX;
                y+=HEIGHT+V_SPACE;
            }
        }

        // set Operation Button

        int opX = digitX+2*(WIDTH+H_SPACE)+H_SPACE;
        int opY = digitY;

        tempX = opX;
        y=opY;
        for(int i=0;i<operationButton.length;i++){
            tempX += WIDTH+H_SPACE;
            operationButton[i] = new OperationButton(tempX,y,WIDTH,HEIGHT,operationButtonText[i],this);
            operationButton[i].setForeground(Color.BLUE);
            if((i+1)%2==0){
                tempX = opX;
                y+=HEIGHT+V_SPACE;
            }
        }

        // Set more Buttons
        tempX = TOPX + (WIDTH+H_SPACE);
        y = TOPY + (HEIGHT + V_SPACE);
        for(int i=0;i<specialButton.length;i++)
        {
            specialButton[i]=new SpecialButton(tempX,y,WIDTH*2,HEIGHT,specialButtonText[i], this);
            specialButton[i].setForeground(Color.RED);
            tempX=tempX+2*WIDTH+H_SPACE;
        }

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {System.exit(0);}
        });

        setLayout(null);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setVisible(true);
    }

    static String getFormattedText(double temp)
    {
        String resText=""+temp;
        if(resText.lastIndexOf(".0")>0)
            resText=resText.substring(0,resText.length()-2);
        return resText;
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

        if(index==0 && cl.displayLabel.getText().equals("0")) return;

        if(cl.setClear){
            cl.displayLabel.setText(""+index);
            cl.setClear=false;
        }
        else{
            cl.displayLabel.setText(cl.displayLabel.getText()+index);
        }
    }
}

class OperationButton extends Button implements ActionListener{
    Calculator cl;
    OperationButton(int x,int y,int width,int height,String cap,Calculator clc){
        super(cap);
        setBounds(x,y,width,height);
        this.cl=clc;
        this.cl.add(this);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev){
        String opText = ((OperationButton)ev.getSource()).getLabel();

        cl.setClear = true;

        double temp = Double.parseDouble(cl.displayLabel.getText());

        if(opText.equals("1/X")){
            try{
                double tempd = 1/ temp;
                cl.displayLabel.setText(Calculator.getFormattedText(tempd));
            }
            catch (ArithmeticException excp){
                cl.displayLabel.setText("Divide by 0");
            }
            return;
        }
        if(opText.equals("sqrt")){
            try{
                double tempd = Math.sqrt(temp);
                cl.displayLabel.setText(Calculator.getFormattedText(tempd));
            }
            catch (ArithmeticException excp){
                cl.displayLabel.setText("Divide by 0 or Negative number");
            }
            return;
        }
        if(!opText.equals("=")){
            cl.number = temp;
            cl.Operation = opText.charAt(0);
            return;
        }

        switch (cl.Operation){

            case '+' :
                temp+=cl.number;break;

            case '-' :
                temp = cl.number - temp;break;

            case '*' :
                temp *= cl.number;break;

            case '/' :
                try {
                    temp=cl.number/temp;
                }
                catch (ArithmeticException excp){
                    cl.displayLabel.setText("Divide by 0");
                    return;
                }
                break;

            case '%' :
                try {
                    temp = cl.number % temp;
                }
                catch (ArithmeticException excp){
                    cl.displayLabel.setText("Divide by 0");
                    return;
                }
                break;
        }

        cl.displayLabel.setText(Calculator.getFormattedText(temp));
    }
}

class SpecialButton extends Button implements ActionListener{
    Calculator cl;

    SpecialButton(int x,int y,int width,int height,String cap,Calculator clc){
        super(cap);
        setBounds(x,y,width,height);
        this.cl=clc;
        this.cl.add(this);
        addActionListener(this);
    }

    static String backSpace(String s){
        String x="";
        for(int i=0;i<s.length()-1;i++) x+=s.charAt(i);
        return  x;
    }

    public void actionPerformed(ActionEvent ev){
        String opText = ((SpecialButton)ev.getSource()).getLabel();

        if(opText.equals("Backspc")){
            String tempText = backSpace(cl.displayLabel.getText());
            if(tempText.equals("")){
                cl.displayLabel.setText("0");
            }
            else{
                cl.displayLabel.setText(tempText);
            }
            return;
        }

        if(opText.equals("C")){
            cl.number =0.0;
            cl.Operation=' ';
            cl.memValue=0.0;
            cl.memLabel.setText(" ");
        }

        cl.displayLabel.setText("0");
        cl.setClear = true;
    }
}
