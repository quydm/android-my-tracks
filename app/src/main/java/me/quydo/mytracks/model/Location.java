package me.quydo.mytracks.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author quydm
 */
@Table(name = "Locations")
public class Location extends Model {

    @Column(name = "track_id")
    public long trackId;

    @Column(name = "created_time")
    public String createdTime;

    @Column
    public String lat;

    @Column
    public String lng;

    @Column
    public String speed;

}
