package edu.northwestu.intc3283.datasourcestarter.entity;

import edu.northwestu.intc3283.datasourcestarter.config.jdbc.converter.ListToMapConverter;
import edu.northwestu.intc3283.datasourcestarter.dto.EntryForm;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Table("entries")
public class Entry {

    @Id
    private Long id;

    private String name;

    private String email;

    @Column("favorite_color")
    private String favoriteColor;

    @CreatedDate
    private Instant createdAt;

    private boolean checkbox;

    @Column("single_select")
    private String singleSelect;

    @Column("selected_items")
    private Map<Long, String> selectedItems;

    @Column("date")
    private Date date;

    @Column("datetime")
    private LocalDateTime datetime;

    public Entry() {
    }

    public Entry(EntryForm form) {
        this.name = form.getName();
        this.email = form.getEmail();
        this.favoriteColor = form.getFavoriteColor();
        this.checkbox = form.isCheckbox();
        this.singleSelect = form.getSingleSelect();
        this.selectedItems = ListToMapConverter.convertListToMap(form.getSelectedItems());
        this.date = form.getDate();
        this.datetime = form.getDatetime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getSingleSelect() {
        return singleSelect;
    }

    public void setSingleSelect(String singleSelect) {
        this.singleSelect = singleSelect;
    }

    public Map<Long, String> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(Map<Long, String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
