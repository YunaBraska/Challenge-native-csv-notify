package com.crossengage.model;

public class User {

    private int id;
    private boolean isActive;
    private Strategies contactBy;
    private String mail;
    private String phone;

    public User(int id, boolean isActive, Strategies contactBy, String mail, String phone) {
        this.id = id;
        this.isActive = isActive;
        this.contactBy = contactBy;
        this.mail = mail;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public Strategies getContactBy() {
        return contactBy;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", contactBy=" + contactBy +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isActive != user.isActive) return false;
        if (contactBy != user.contactBy) return false;
        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;
        return phone != null ? phone.equals(user.phone) : user.phone == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (contactBy != null ? contactBy.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
