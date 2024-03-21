package org.example.tables;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(schema = "movie", name = "store")
public class Store {
    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @OneToOne
    @JoinColumn(name = "manager_staff_id")
    private Staff staff;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public void setId(Byte id) {
        this.id = id;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Byte getId() {
        return id;
    }

    public Staff getStaff() {
        return staff;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
