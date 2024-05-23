/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_layer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 *
 * @author Do Dat
 */
public class FileManager {
    public boolean saveDataToFile(HashMap<String, Object> data, String fileName, String msg) {
        boolean success = false; // Biến để theo dõi việc lưu dữ liệu thành công hay không
        try {
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(data); // Ghi toàn bộ HashMap vào tệp

            oos.close();
            fos.close();
            success = true; // Đánh dấu việc lưu thành công
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (success) {
                System.out.println(msg);
            }
        }
        return success; // Trả về kết quả sau khi đã xử lý tất cả các exception
    }

    public boolean loadDataFromFile(HashMap<String, Object> data, String fileName) {
        boolean success = false; // Biến để theo dõi việc đọc dữ liệu thành công hay không
        try {
            // Kiểm tra tệp tồn tại
            File file = new File(fileName);
            if (!file.exists()) {
                return false;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

            // Đọc toàn bộ HashMap từ tệp và gán cho biến data
            data.putAll((HashMap<String, Object>) ois.readObject());

            ois.close();
            success = true; // Đánh dấu việc đọc thành công
        } catch (FileNotFoundException e) {
            // Xử lý khi không tìm thấy tệp
            System.out.println(e);
        } catch (IOException | ClassNotFoundException e) {
            // Xử lý khi gặp lỗi đọc dữ liệu từ tệp
            System.out.println(e);
        } finally {
            // Trả về kết quả sau khi đã xử lý tất cả các exception
            return success;
        }
    }

}
