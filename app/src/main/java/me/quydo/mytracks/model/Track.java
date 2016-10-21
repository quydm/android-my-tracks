package me.quydo.mytracks.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author quydm
 */
@Table(name = "Tracks")
public class Track extends Model {

    @Column(name = "track_name")
    public String trackName;

    @Column(name = "created_time")
    public String createdTime;

}
