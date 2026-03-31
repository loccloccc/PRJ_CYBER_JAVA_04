package prj.util;

import java.util.Scanner;

public class ValidateInput {
    public int inputChoice(Scanner sc ){
        int choice;
        while (true) {
            System.out.print("Nhập lựa chọn: ");

            try {
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Không được để trống.");
                    continue;
                }

                choice = Integer.parseInt(input);

                if (choice < 0) {
                    System.out.println("Lựa chọn phải > 0.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số.");
            }
        }
        return choice;
    }
}
