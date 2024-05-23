/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package check_data;

import java.util.Scanner;

/**
 *
 * @author Do Dat
 */
public class Tools {
    /**
     * Phương thức dùng để lấy thông tin được nhập từ người dùng
     * @param msg
     * @return 
     */
    public static String inputStr(String msg) {
        String result;
        Scanner sc = new Scanner(System.in);
        System.out.print(msg);
        result = sc.nextLine();
        return result;
    }
    
    /**
     * Phương thức dùng để báo lỗi khi nhập sai
     */
    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }
    
    /**
     * Phương thức dùng để nhập code và name
     * @param msg
     * @param pattern
     * @return 
     */
    public static String inputCharacter(String msg, String pattern) {
        String data = "";
        boolean lapNua = true; // Change false to true

        while (lapNua) {            
            try {
                data = inputStr(msg).trim().toUpperCase();
                if (data.isEmpty()) {
                    break;
                }
                if (!data.matches(pattern)) {
                    // kiểm tra pattern nào sẽ in ra lỗi đó
                    if (pattern.equals("DR\\d{3}")) {
                        throw new InvalidInputException("Invalid input!!! Please enter in the format DRxxx (x is number)");                       
                    } else if (pattern.equals("TP\\d{3}")) {
                        throw new InvalidInputException("Invalid input!!! Please enter in the format TPxxx (x is number)");
                    } else {
                        throw new InvalidInputException("Invalid input!!! Only words are allowed.");
                    }
                }
                lapNua = false;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input!!! Please enter again!!!");
            }
        }
        return data;
    }
   
    /**
     * Phương thức dùng để nhập lựa chọn và số lượng thành phần
     * @param msg
     * @return 
     */
    public static Double inputNumber(String msg) {
        Double number = null;
        boolean lapNua = true;

        while (lapNua) {
            try {
                String data = inputStr(msg).trim();

                if (data.isEmpty()) {
                    return null; // Trả về null nếu dữ liệu trống
                } else if (data.matches("-?\\d+")) { // Thêm dấu -? để cho phép số âm
                    number = Double.parseDouble(data);
                } else if (data.matches("-?\\d+\\.\\d+")) { // Thêm dấu -? để cho phép số âm
                    number = Double.parseDouble(data);
                } else {
                    throw new NumberFormatException();
                }
                lapNua = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!!! Please enter a valid number!!!");
            }
        }
        return number;
    }
    
    /**
     * Phương thức để nhập yes no
     * @param msg
     * @return 
     */
    public static boolean inputYN(String msg) {
        String data;
        boolean lapNua = true;
        while (lapNua) {            
            try {
                data = inputStr(msg);
                if (data.equalsIgnoreCase("Y")) {
                    return true;
                } else if (data.equalsIgnoreCase("N")) {
                    return false;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input!!! Must be Y or N!!!");
                lapNua = true;
            }
        }
        return false;
    }
}
