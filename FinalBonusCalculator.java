import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.border.Border;

public class FinalBonusCalculator{
  class ScientificCalculator extends JFrame {

    private JTextField textField;
    private StringBuilder input;

    public ScientificCalculator() {
      setTitle("Scientific Calculator");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400, 600);
      setLayout(new BorderLayout());

      input = new StringBuilder();
      textField = new JTextField();
      textField.setFont(new Font("Arial", Font.PLAIN, 24));
      textField.setEditable(false);//I don't want the user to screw things up
      add(textField, BorderLayout.NORTH);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(8, 5, 10, 10));
      String[] buttonLabels = {
        "7", "8", "9", "/", "(",
        "4", "5", "6", "*", ")",
        "1", "2", "3", "-", "^",//Be careful may cause an issue
        "0", "Clear", "=", "+","mod", 
        "sin(", "cos(", "tan(", "log(", "pi",
        "atan(", "asin(", "acos(", "log10(", "sqrt(",//New line added
        "sec(", "csc(", "cot(", "^(2)", "^(3)",
        "acsc(", "asec(", "acot(", "^(-1)"
      };

      for (String label : buttonLabels) {
        JButton button = new JButton(label);
        button.addActionListener(new ButtonClickListener());
        //Changing the font and size
        Font font = new Font("Arial", Font.BOLD, 20);
        button.setFont(font);
        //Add the button to the panel
        buttonPanel.add(button);
        //Customize the button shape and color 
        button.setBorder(BorderFactory.createLineBorder(Color.RED));
        //button.setBorder(BorderFactory.createRoundedBorder(10));

        if("1234567890".contains(label)){button.setBackground(Color.BLUE);}
        else if("()^+-*/mod".contains(label)){button.setBackground(Color.cyan);}
        else if(label.equals("Clear") || label.equals("=")){button.setBackground(Color.magenta);}
        else if(label.equals("^(-1)") || label.equals("^(2)") || label.equals("^(3)") || label.equals("sqrt(") ||
                label.equals("log(") || label.equals("log10(")){button.setBackground(Color.orange);}
        else if(label.equals("pi")){button.setBackground(Color.yellow);}
        else if("cos(sin(tan(sec(csc(cot(asin(acos(atan(asec(acsc(acot(".contains(label)){button.setBackground(Color.green);}
      }

      add(buttonPanel, BorderLayout.CENTER);
      setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (buttonText.equals("=")) {
          evaluateExpression();
        }
        else if(buttonText.equals("Clear")){//Was changed from C to Clear
          clear();
        }else{
          input.append(buttonText);
          textField.setText(input.toString());
        }
      }
    }

    private void evaluateExpression() {
      try {
        String expression = textField.getText();
        //System.out.println("line 1");
        
        //double result = evaluateInfix(expression);
        String result = Double.toString(AllinOne(expression));

        System.out.println("line 2");
        textField.setText(String.valueOf(result));

        System.out.println("line 3");
        input.setLength(0);
        System.out.println("line 4");
        input.append(result);
      }
      catch(Exception ex){
        textField.setText("Error");
        input.setLength(0);
      }
    }

    private void clear(){
      input.setLength(0);
      textField.setText("");
    }

    public String whichFuncOrOp(String expEval, int startPos){
      int i = 0;    
      while(startPos + i < expEval.length() && i < 5){i++;}
      String specificPlace5 = expEval.substring(startPos, startPos + i);
      if(specificPlace5.equals("log10")){return "log10";}

      i = 0;
      while(startPos + i < expEval.length() && i < 4){i++;}
      String specificPlace4 = expEval.substring(startPos, startPos + i);
      switch(specificPlace4){
        case("asin"):
          return "asin";
        case("acos"):
          return "acos";
        case("atan"):
          return "atan";
        case("acsc"):
          return "acsc";
        case("asec"):
          return "asec";
        case("acot"):
          return "acot";
        case("sqrt"):
          return "sqrt";
      }

      i = 0;
      while(startPos + i < expEval.length() && i < 3){i++;}
      String specificPlace3 = expEval.substring(startPos, startPos + i);
      switch(specificPlace3){
        case("sin"):
          return "sin";
        case("cos"):
          return "cos";
        case("tan"):
          return "tan";
        case("csc"):
          return "csc";
        case("sec"):
          return "sec";
        case("cot"):
          return "cot";
        case("log"):
          return "log";
        case("mod"):
          return "mod";
      }
      
      i = 0;
      while(startPos + i < expEval.length() && i < 2){i++;}
      String specificPlace2 = expEval.substring(startPos, startPos + i);
      if(specificPlace2.equals("pi")){return "pi";}//I am trying to change this to pi cause this is 
      //what it is compared to anyway
      //System.out.println(expEval.charAt(startPos) + " : "+ startPos);
      String specificChar = Character.toString(expEval.charAt(startPos));
      switch(specificChar){
        case("+"):
          System.out.println("+ HERE");
          return "+";
        case("-"):
          return "-";
        case("*"):
          System.out.println("* HERE");
          return "*";
        case("/"):
          return "/";
        case("^"):
          return "^";
        case("("):
          return "(";
        case(")"):
          return ")";
        case("0"):
          return "0";
        case("1"):
          return "1";
        case("2"):
          return "2";
        case("3"):
          return "3";
        case("4"):
          return "4";
        case("5"):
          return "5";
        case("6"):
          System.out.println("6 HERE");
          return "6";
        case("7"):
          System.out.println("7 HERE");
          return "7";
        case("8"):
          return "8";
        case("9"):
          System.out.println("9 HERE");
          return "9";
      }

      //System.out.println("whichFuncOrOp failed at this point: " + expEval.charAt(startPos));
      return "What the hell just happened!";
    }
  
  public int jumpToWhichInd(String funcObtain){
    switch(funcObtain.length()){
      case(1):
        return 1;
      case(3):
        return 3;
      case(4):
        return 4;
      case(5):
        return 5;
      case(17):
        return 17;
    }
    System.out.println("jumpToWhichInd failed at: " + funcObtain);
    return -99;
  }

  public int precedence(String funcObtain){
    switch(funcObtain){
      case("+"):
        return 4;
      case("-"):
        return 4;
      case("*"):
        return 3;
      case("/"):
        return 3;
      case("^"):
        return 2;
      case("cos"):
        return 2;
      case("sin"):
        return 2;
      case("tan"):
        return 2;
      case("csc"):
        return 2;
      case("sec"):
        return 2;
      case("cot"):
        return 2;
      case("asin"):
        return 2;
      case("acos"):
        return 2;
      case("atan"):
        return 2;
      case("acsc"):
        return 2;        
      case("acot"):
        return 2;
      case("mod"):
        return 2;
      case("log"):
        return 2;
      case("log10"):
        return 2;
      case("sqrt"):
        return 2;
      case("<END>"):
        return 6;
      case("("):
        return 6;
      case(")"):
        return 6;
    }
    System.out.println("precedence failed at: " + funcObtain);
    return -99;
  }

public boolean isFunc(String funcObtain){
    switch(funcObtain){
      case("+"):
        return true;
      case("-"):
        return true;
      case("*"):
        return true;
      case("/"):
        return true;
      case("^"):
        return true;
      case("cos"):
        return true;
      case("sin"):
        return true;
      case("tan"):
        return true;
      case("csc"):
        return true;
      case("sec"):
        return true;
      case("cot"):
        return true;
      case("asin"):
        return true;
      case("acos"):
        return true;
      case("atan"):
        return true;
      case("acsc"):
        return true;        
      case("acot"):
        return true;
      case("mod"):
        return true;
      case("log"):
        return true;
      case("log10"):
        return true;
      case("("):
        return true;
      case(")"):
        return true;
      case("sqrt"):
        return true;
      default:
        return false;
    }
  }
  
  

  public ArrayList<String> lookFor(String expression){
    ArrayList<String> expressionDivided = new ArrayList<String>();
    int i = 0;
    String result;
    while(i < expression.length()){//PI SHOULD GO THROUGH HERE RETARD
      result = whichFuncOrOp(expression, i);
      //System.out.println("What the hell do we have here: " + result);
      if(isFunc(result)){
        expressionDivided.add(result);
        i += jumpToWhichInd(result);//This is just for test 
      }
      else if(result == "pi"){
        System.out.println(Math.PI);
        expressionDivided.add(Double.toString(Math.PI));
        i += 2;
      }
      else{
        result = "";
        while(i < expression.length() && !isFunc(whichFuncOrOp(expression, i))){//Test this part PLEASE DO NOT FORGET MAY CAUSE A LOTTA TROUBLE  
          result += expression.charAt(i);
          i ++;
          //System.out.println("I: " + i + " result: " + result + " expression: " + expression + " result at I: " + expression.charAt(i));
        }
        //System.out.println("Currently lookFor index: " + i);

        expressionDivided.add(result);
      }
    }
    //System.out.println("This is the length() of pi: " + "3.141592653589793".length());
    System.out.println("Infix: " + expressionDivided);
    return expressionDivided;
  }
  
  //This function works fine but ONLY FOR ONE DIGIT EXPRESSIONS
  public ArrayList<String> toPostfix(ArrayList<String> expEval){
    Stack<String> tempStack = new Stack<String>();
    //String postFixExp = "";
    ArrayList<String> finalPostFix= new ArrayList<String>();
    tempStack.push("<END>");
    int i = 0;
    String result;
    while(i < expEval.size()){//The original before arraylist input was just expEval.length()
      //result = whichFuncOrOp(expEval.get(i), i);//The original before arraylist input was just expEval
      result = expEval.get(i);
      //System.out.println(result);//This line is for debugging
      if(isFunc(result)){
        if(result == "("){//get(i) was added
          tempStack.push("(");
        }
        else if(result == ")"){
          while(tempStack.peek() != "("){
            finalPostFix.add(tempStack.pop());
            //postFixExp += tempStack.pop(); //changed from < tempStack.pop() > //This line was used for string return 
          }
          tempStack.pop();
          //System.out.println("What the hell just happened?");
        }
        else if(precedence(result) < precedence(tempStack.peek())){
          tempStack.push(result);
          //if(result == "+"){System.out.println("Plus sign caught");}//This line is for debugging 2
          //System.out.println(tempStack.peek());
        }
        else if(precedence(result) >= precedence(tempStack.peek())){
          while(precedence(result) >= precedence(tempStack.peek())){
            finalPostFix.add(tempStack.peek());
            //postFixExp += tempStack.peek(); //This was used for string return
            tempStack.pop();
          }
          //if(result == "+"){System.out.println("Plus sign caught again");}//This line is for debugging 2
          tempStack.push(result);
        }
        else{System.out.println("Please just work!");}
      }
      else{
        finalPostFix.add(result);
        //postFixExp += result; //This line was used for string return
      }

      //i += jumpToWhichInd(result);//The original before arraylist input 
      i++;
    }
    while(!tempStack.isEmpty() && tempStack.peek() != "<END>"){
      //System.out.println("This is remainder: " + tempStack.peek());//This line is for debugging
      finalPostFix.add(tempStack.pop());
      //postFixExp += tempStack.pop(); //This line was used string return 
    }
    //return postFixExp;
    System.out.println("postfix: " + finalPostFix);
    return finalPostFix;
  }
  

  public double postFixEvaluator(ArrayList<String> postfix){
    Stack<Double> tempStack = new Stack<Double>();
    int i = 0;
    String currentElem;
    Double currentElemDouble;
    while(i < postfix.size()){
      currentElem = postfix.get(i);
      try{
        currentElemDouble = Double.parseDouble(currentElem);
        tempStack.push(currentElemDouble);
        //System.out.println("pushed into stack: " + tempStack.peek());//Debugging
      }
      catch(NumberFormatException e){
        //System.out.println(tempStack.peek());//Debugging
        //Double firstOperand = 1;//These two lines are to be debugged
        //if(!tempStack.isEmpty()){firstOperand = tempStack.pop();}//These two lines are to be debugged
        Double firstOperand = tempStack.pop();
        Double secondOperand = 0.0;
        if(currentElem == "+" || currentElem == "-" || currentElem == "*" || currentElem == "/" || currentElem == "^" || currentElem == "mod")
          {
          //System.out.println("Last Element: " + tempStack.peek());//Debugging
          secondOperand = tempStack.pop();
        }
        switch(currentElem){
          case("+"):
            tempStack.push(secondOperand + firstOperand);
            //System.out.println(secondOperand + " + " + firstOperand);//Debugging
            break;
          case("-"):
            tempStack.push(secondOperand - firstOperand);
            //System.out.println(secondOperand + " - "  + firstOperand);//Deubgging
            break;
          case("*"):
            tempStack.push(secondOperand * firstOperand);
            //System.out.println(secondOperand + " * "  + firstOperand);//Debugging
            break;
          case("/"):
            tempStack.push(secondOperand / firstOperand);
            //System.out.println(secondOperand + " / "  + firstOperand);//Debugging
            //System.out.println(secondOperand / firstOperand);
            break;
          case("^"):
            tempStack.push(Math.pow(secondOperand, firstOperand));
            //System.out.println(secondOperand + " ^ "  + firstOperand);//Debugging
            break;
          case("log"):
            tempStack.push(Math.log(firstOperand));
            break;
          case("log10"):
            tempStack.push(Math.log10(firstOperand));
            break;
          case("mod"):
            tempStack.push(secondOperand % firstOperand);
            break;
          case("sin"):
            tempStack.push(Math.sin(firstOperand));
            break;
          case("cos"):
            tempStack.push(Math.cos(firstOperand));
            //System.out.println(Math.cos(firstOperand));
            break;
          case("tan"):
            tempStack.push(Math.tan(firstOperand));
            break;
          case("csc"):
            tempStack.push(1 / Math.sin(firstOperand));
            break;
          case("sec"):
            tempStack.push(1 / Math.cos(firstOperand));
            break;
          case("cot"):
            tempStack.push(1 / Math.tan(firstOperand));
            break;
          case("acot"):
            tempStack.push(Math.atan(1 / firstOperand));
            break;
          case("acsc"):
            tempStack.push(Math.asin(1 / firstOperand));
            break;
          case("asec"):
            tempStack.push(Math.acos(1 / firstOperand));
            break;
          case("asin"):
            tempStack.push(1 / Math.asin(firstOperand));
            break;
          case("acos"):
            tempStack.push(1 / Math.acos(firstOperand));
            //System.out.println(Math.acos(firstOperand));
            break;
          case("atan"):
            tempStack.push(1 / Math.atan(firstOperand));
            break;
          case("sqrt"):
            tempStack.push(Math.sqrt(firstOperand));
            break;
        }
      }
      //System.out.println(tempStack.peek());
      i++;
    }
    return tempStack.pop();
  }
   
  public double AllinOne(String expression){
    return postFixEvaluator(toPostfix(lookFor(expression)));
  }


    
    /*
    public String whichFuncOrOp(String expEval, int startPos){
      int i = 0;    
      while(startPos + i < expEval.length() && i < 5){i++;}
      String specificPlace5 = expEval.substring(startPos, startPos + i);
      if(specificPlace5.equals("log10")){return "log10";}

      i = 0;
      while(startPos + i < expEval.length() && i < 3){i++;}
      String specificPlace3 = expEval.substring(startPos, startPos + i);
      switch(specificPlace3){
        case("sin"):
          return "sin";
        case("cos"):
          return "cos";
        case("tan"):
          return "tan";
        case("csc"):
          return "csc";
        case("sec"):
          return "sec";
        case("cot"):
          return "cot";
        case("log"):
          return "log";
        case("mod"):
          return "mod";
      }
      
      i = 0;
      while(startPos + i < expEval.length() && i < 4){i++;}
      String specificPlace4 = expEval.substring(startPos, startPos + i);
      switch(specificPlace4){
        case("asin"):
          return "asin";
        case("acos"):
          return "acos";
        case("atan"):
          return "atan";
        case("acsc"):
          return "acsc";
        case("asec"):
          return "asec";
        case("acot"):
          return "acot";
        case("sqrt"):
          return "sqrt";
      }

      String specificChar = Character.toString(expEval.charAt(startPos));
      switch(specificChar){
        case("+"):
          return "+";
        case("-"):
          return "-";
        case("*"):
          return "*";
        case("/"):
          return "/";
        case("^"):
          return "^";
        case("("):
          return "(";
        case(")"):
          return ")";
        case("0"):
          return "0";
        case("1"):
          return "1";
        case("2"):
          return "2";
        case("3"):
          return "3";
        case("4"):
          return "4";
        case("5"):
          return "5";
        case("6"):
          return "6";
        case("7"):
          return "7";
        case("8"):
          return "8";
        case("9"):
          return "9";
      }

      System.out.println("whichFuncOrOp failed!");
      return "What the hell just happened!";
    }
  
  public int jumpToWhichInd(String funcObtain){
    switch(funcObtain.length()){
      case(1):
        return 1;
      case(3):
        return 3;
      case(4):
        return 4;
      case(5):
        return 5;
    }
    System.out.println("jumpToWhichInd failed!");
    return -99;
  }

  public int precedence(String funcObtain){
    switch(funcObtain){
      case("+"):
        return 4;
      case("-"):
        return 4;
      case("*"):
        return 3;
      case("/"):
        return 3;
      case("^"):
        return 2;
      case("cos"):
        return 2;
      case("sin"):
        return 2;
      case("tan"):
        return 2;
      case("csc"):
        return 2;
      case("sec"):
        return 2;
      case("cot"):
        return 2;
      case("asin"):
        return 2;
      case("acos"):
        return 2;
      case("atan"):
        return 2;
      case("acsc"):
        return 2;        
      case("acot"):
        return 2;
      case("mod"):
        return 2;
      case("log"):
        return 2;
      case("log10"):
        return 2;
      case("sqrt"):
        return 2;
      case("<END>"):
        return 6;
      case("("):
        return 6;
      case(")"):
        return 6;
    }
    System.out.println("precedence failed!");
    return -99;
  }

public boolean isFunc(String funcObtain){
    switch(funcObtain){
      case("+"):
        return true;
      case("-"):
        return true;
      case("*"):
        return true;
      case("/"):
        return true;
      case("^"):
        return true;
      case("cos"):
        return true;
      case("sin"):
        return true;
      case("tan"):
        return true;
      case("csc"):
        return true;
      case("sec"):
        return true;
      case("cot"):
        return true;
      case("asin"):
        return true;
      case("acos"):
        return true;
      case("atan"):
        return true;
      case("acsc"):
        return true;        
      case("acot"):
        return true;
      case("mod"):
        return true;
      case("log"):
        return true;
      case("log10"):
        return true;
      case("sqrt"):
        return true;     
      case("("):
        return true;
      case(")"):
        return true;
      default:
        return false;
    }
  }

  public ArrayList<String> toPostfix(String expEval){
    Stack<String> tempStack = new Stack<String>();
    String postFixExp = "";
    ArrayList<String> finalPostFix= new ArrayList<String>();
    tempStack.push("<END>");
    int i = 0;
    while(i < expEval.length()){
      String result = whichFuncOrOp(expEval, i);
      //System.out.println(result);//This line is for debugging
      if(isFunc(result) == true){
        if(whichFuncOrOp(expEval, i) == "("){
          tempStack.push("(");
        }
        else if(whichFuncOrOp(expEval, i) == ")"){
          while(tempStack.peek() != "("){
            finalPostFix.add(tempStack.pop());
            //postFixExp += tempStack.pop(); //changed from < tempStack.pop() > //This line was used for string return 
          }
          tempStack.pop();
          //System.out.println("What the hell just happened?");
        }
        else if(precedence(result) < precedence(tempStack.peek())){
          tempStack.push(result);
          if(result == "+"){System.out.println("Plus sign caught");}//This line is for debugging 2
          //System.out.println(tempStack.peek());
        }
        else if(precedence(result) >= precedence(tempStack.peek())){
          while(precedence(result) >= precedence(tempStack.peek())){
            finalPostFix.add(tempStack.peek());
            //postFixExp += tempStack.peek(); //This was used for string return
            tempStack.pop();
          }
          if(result == "+"){System.out.println("Plus sign caught again");}//This line is for debugging 2
          tempStack.push(result);
        }
        else{System.out.println("Please just work!");}
      }
      else{
        finalPostFix.add(result);
        //postFixExp += result; //This line was used for string return
      }

      i += jumpToWhichInd(result);
    }
    while(!tempStack.isEmpty() && tempStack.peek() != "<END>"){
      System.out.println("This is remainder: " + tempStack.peek());
      finalPostFix.add(tempStack.pop());
      //postFixExp += tempStack.pop(); //This line was used string return 
    }
    //return postFixExp;
    System.out.println(finalPostFix);
    return finalPostFix;
  }

  public double postFixEvaluator(ArrayList<String> postfix){
    Stack<Double> tempStack = new Stack<Double>();
    int i = 0;
    String currentElem;
    Double currentElemDouble;
    while(i < postfix.size()){
      currentElem = postfix.get(i);
      try{
        currentElemDouble = Double.parseDouble(currentElem);
        tempStack.push(currentElemDouble);
        //System.out.println("pushed into stack: " + tempStack.peek());//Debugging
      }
      catch(NumberFormatException e){
        //System.out.println(tempStack.peek());//Debugging
        //Double firstOperand = 1;//These two lines are to be debugged
        //if(!tempStack.isEmpty()){firstOperand = tempStack.pop();}//These two lines are to be debugged
        Double firstOperand = tempStack.pop();
        Double secondOperand = 0.0;
        if(currentElem == "+" || currentElem == "-" || currentElem == "*" || currentElem == "/" || currentElem == "^" || currentElem == "mod")
          {
          //System.out.println("Last Element: " + tempStack.peek());//Debugging
          secondOperand = tempStack.pop();
        }
        switch(currentElem){
          case("+"):
            tempStack.push(secondOperand + firstOperand);
            //System.out.println(secondOperand + " + " + firstOperand);//Debugging
            break;
          case("-"):
            tempStack.push(secondOperand - firstOperand);
            //System.out.println(secondOperand + " - "  + firstOperand);//Deubgging
            break;
          case("*"):
            tempStack.push(secondOperand * firstOperand);
            //System.out.println(secondOperand + " * "  + firstOperand);//Debugging
            break;
          case("/"):
            if(firstOperand != 0){tempStack.push(secondOperand / firstOperand);}
            //System.out.println(secondOperand + " / "  + firstOperand);//Debugging
            break;
          case("^"):
            tempStack.push(Math.pow(secondOperand, firstOperand));
            //System.out.println(secondOperand + " ^ "  + firstOperand);//Debugging
            break;
          case("log"):
            tempStack.push(Math.log(firstOperand));
            break;
          case("log10"):
            tempStack.push(Math.log10(firstOperand));
            break;
          case("mod"):
            tempStack.push(secondOperand % firstOperand);
            break;
          case("sin"):
            tempStack.push(Math.sin(firstOperand));
            break;
          case("cos"):
            tempStack.push(Math.cos(firstOperand));
            break;
          case("tan"):
            tempStack.push(Math.tan(firstOperand));
            break;
          case("csc"):
            tempStack.push(1 / Math.sin(firstOperand));
            break;
          case("sec"):
            tempStack.push(1 / Math.cos(firstOperand));
            break;
          case("cot"):
            tempStack.push(1 / Math.tan(firstOperand));
            break;
          case("acot"):
            tempStack.push(Math.atan(1 / firstOperand));
            break;
          case("acsc"):
            tempStack.push(Math.asin(1 / firstOperand));
            break;
          case("asec"):
            tempStack.push(Math.acos(1 / firstOperand));
            break;
          case("asin"):
            tempStack.push(1 / Math.asin(firstOperand));
            break;
          case("acos"):
            tempStack.push(1 / Math.acos(firstOperand));
            break;
          case("atan"):
            tempStack.push(1 / Math.atan(firstOperand));
            break;
          case("sqrt"):
            tempStack.push(Math.sqrt(firstOperand));
            break;
          default:
            System.out.println("This expression is unrecognized lol");
            break;
        }
      }
      System.out.println(tempStack.peek());
      i++;
    }
    System.out.println(tempStack.peek());
    return tempStack.pop();
  }
   
  public double AllinOne(String expression){
    return postFixEvaluator(toPostfix(expression));
  }
  */
  
 }
  public static void main(String[] args){
    FinalBonusCalculator program = new FinalBonusCalculator();
    SwingUtilities.invokeLater(() -> program.new ScientificCalculator());
  }
}

