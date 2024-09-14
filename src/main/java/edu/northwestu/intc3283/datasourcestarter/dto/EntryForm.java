package edu.northwestu.intc3283.datasourcestarter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class EntryForm {

    @Size(min = 5, max = 50)
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String favoriteColor;

    @NotNull
    private boolean checkbox;

    @NotNull
    private String singleSelect;

    @NotEmpty
    private List<String> selectedItems;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime datetime;

    public @Size(min = 5, max = 50) @NotEmpty String getName() {
        return name;
    }

    public void setName(@Size(min = 5, max = 50) @NotEmpty String name) {
        this.name = name;
    }

    public @Email @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty @Size(min = 3, max = 20) String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(@NotEmpty @Size(min = 3, max = 20) String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public @NotNull String getSingleSelect() {
        return singleSelect;
    }

    public void setSingleSelect(@NotNull String singleSelect) {
        this.singleSelect = singleSelect;
    }

    public @NotEmpty List<String> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(@NotEmpty List<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public @NotNull Date getDate() {
        return date;
    }

    public void setDate(@NotNull Date date) {
        this.date = date;
    }

    public @NotNull LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(@NotNull LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
