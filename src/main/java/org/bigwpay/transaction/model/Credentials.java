package org.bigwpay.transaction.model;

public final class Credentials {

    public static Credentials of(String userName, String password) {
        return new Credentials(userName, password);
    }


    private final String userName;
    private final String password;

    private Credentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "userName='" + userName + '\'' +
                ", password=******" +
                '}';
    }
}
