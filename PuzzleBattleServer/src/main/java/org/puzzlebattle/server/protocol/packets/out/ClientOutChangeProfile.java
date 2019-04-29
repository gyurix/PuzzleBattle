package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ClientOutChangeProfile extends ClientOutPacket {
  private byte[] avatar;
  private Date dateOfBirth;
  private int id;
  private Timestamp lastLogin;
  private String name, surname, nickName, email, password;
  private Timestamp registered;

  private int convertAge(Date dateOfBirthSQL) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateOfBirthSQL);
    int year = calendar.get(Calendar.YEAR);
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    return currentYear - year;
  }

  private String convertDateOfBirth(Date dateOfBirthSQL) {
    DateFormat df;
    String convertedDate;
    Calendar calendar;
    df = new SimpleDateFormat("MM/dd/yyyy");
    convertedDate = df.format(dateOfBirthSQL);

    return convertedDate;
  }

  private String convertTimestampToString(Timestamp timestamp) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String string = dateFormat.format(timestamp);
    return string;
  }

  public void write(ByteBuf buf) {
    writeString(buf, nickName);
    writeString(buf, password);
    writeString(buf, name);
    writeString(buf, surname);
    writeString(buf, email);
    writeString(buf, convertDateOfBirth(dateOfBirth));
    buf.writeInt(convertAge(dateOfBirth));
    buf.writeBytes(avatar);
  }
}
