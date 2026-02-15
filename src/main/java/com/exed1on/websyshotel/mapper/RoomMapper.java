package com.exed1on.websyshotel.mapper;

import com.exed1on.websyshotel.dto.response.RoomResponse;
import com.exed1on.websyshotel.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getType(),
                room.getPrice(),
                room.getAmenities(),
                room.getDescription()
        );
    }

    public Room toEntity(RoomResponse response) {
        return new Room(
                response.getId(),
                response.getName(),
                response.getType(),
                response.getPrice(),
                response.getAmenities(),
                response.getDescription()
        );
    }
}
