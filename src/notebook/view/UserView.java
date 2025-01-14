package notebook.view;

import notebook.model.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;

import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ").toUpperCase();
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    User u = createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    String id = prompt("Выберете идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case READALL:
                    System.out.println(userController.readAll());
                    break;
                case UPDATE:
                    String userId = prompt("Выберете идентификатор пользователя: ");
                    userController.updateUser(userId, createUser());
                case DELETE:
                    String Id = prompt("Выберете идентификатор пользователя: ");
                    try {
                        Long IdDelete = Long.parseLong(Id);
                        boolean deleted = userController.deleteUser(IdDelete);
                        if (deleted) {
                            System.out.println("Пользователь успешно удален.");
                        } else {
                            System.out.println("Пользователь не найден или произошла ошибка при удалении.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректный формат идентификатора.");
                    }
                    break;
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
    public String checkLine(String str) {
        str = str.trim().replace(" ","");
        if (!str.isEmpty()) {
            return str;
        } else {
            System.out.println("Значение не может быть пустым.\n");
            str = prompt("Введите корректные данные");
            return checkLine(str);
        }
}

    private User createUser() {
        String firstName = checkLine(prompt("Имя: "));
        String lastName = checkLine(prompt("Фамилия: "));
        String phone = checkLine(prompt("Номер телефона: "));
        return new User(firstName, lastName, phone);
    }
}
