package cn.caohongliang.gray.core.util.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class GsonTypeAdapterFactory implements TypeAdapterFactory {
	private static final DateTimeFormatter localDateTimeNanoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		Class<? super T> rawType = typeToken.getRawType();
		if (LocalDateTime.class.isAssignableFrom(rawType)) {
			return (TypeAdapter<T>) createLocalDateTime();
		} else if (LocalDate.class.isAssignableFrom(rawType)) {
			return (TypeAdapter<T>) createLocalDate();
		} else if (Date.class.isAssignableFrom(rawType)) {
			return (TypeAdapter<T>) createDate();
		}
		return null;
	}

	private TypeAdapter<LocalDateTime> createLocalDateTime() {
		return new TypeAdapter<LocalDateTime>() {
			@Override
			public void write(JsonWriter out, LocalDateTime value) throws IOException {
				String time = value.format(localDateTimeNanoFormatter);
				out.value(time);
			}

			@Override
			public LocalDateTime read(JsonReader in) throws IOException {
				String timeStr = in.nextString();
				return LocalDateTime.parse(timeStr, localDateTimeNanoFormatter);
			}
		};
	}

	private TypeAdapter<LocalDate> createLocalDate() {
		return new TypeAdapter<LocalDate>() {
			@Override
			public void write(JsonWriter out, LocalDate value) throws IOException {
				String time = value.format(localDateFormatter);
				out.value(time);
			}

			@Override
			public LocalDate read(JsonReader in) throws IOException {
				String timeStr = in.nextString();
				return LocalDate.parse(timeStr, localDateFormatter);
			}
		};
	}

	private TypeAdapter<Date> createDate() {
		return new TypeAdapter<Date>() {
			@Override
			public void write(JsonWriter out, Date value) throws IOException {
				String time = dateFormatter.format(value);
				out.value(time);
			}

			@Override
			public Date read(JsonReader in) throws IOException {
				String timeStr = in.nextString();
				try {
					return dateFormatter.parse(timeStr);
				} catch (ParseException e) {
					log.error("Gson转换Date异常：{}", timeStr, e);
					return null;
				}
			}
		};
	}
}
