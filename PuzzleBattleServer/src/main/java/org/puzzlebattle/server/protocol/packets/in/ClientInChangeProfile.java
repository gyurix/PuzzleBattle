package org.puzzlebattle.server.protocol.packets.in;


import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.db.entity.PBUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

@Data
public class ClientInChangeProfile extends ClientInPacket {
  private PBUser user;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    user = new PBUser();
    String name = ByteBufUtils.readString(buf);
    String surname = ByteBufUtils.readString(buf);
    String dateOfBirth = ByteBufUtils.readString(buf);
    byte avatar[] = ByteBufUtils.readBytes4(buf);
    user.setName(name);
    user.setAvatar(avatar);
    user.setSurname(surname);
    user.setDateOfBirth(convertStringToDate(dateOfBirth));
  }

  /**
   * Conversion string date to sql date, for storing into database
   *
   * @param dateToConvert string representation of date which should be converted
   * @return sql date prepared to be stored into database
   * @throws ParseException if there is error while convertion
   */
  public static Date convertStringToDate(String dateToConvert){
    Date sqlDate = null;
    try {
      SimpleDateFormat simpleDateF = new SimpleDateFormat("dd. MM. yyyy");
      java.util.Date date = simpleDateF.parse(dateToConvert);
      sqlDate = new Date(date.getTime());
    }
    catch(ParseException e){
      Logging.logSevere("Cannot convert string to date!",e);
    }
    return sqlDate;
  }
}
