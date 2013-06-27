package com.qburst.newsreader.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.qburst.newsreader.app.NRApp;

/**
 * @author user utility methods are defined here.
 */

@SuppressLint({ "SimpleDateFormat", "SdCardPath", "WorldReadableFiles" })
public class NRUtility {
	public static boolean editmode = false;

	public static String convertStreamToString(InputStream is)
			throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";

		}
	}

	public static String convertPdfStreamToString(InputStream is)
			throws Exception {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-16"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";

		}
	}

	public static String getMd5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String md5 = number.toString(16);
			while (md5.length() < 32)
				md5 = "0" + md5;
			return md5;
		} catch (NoSuchAlgorithmException e) {
			Log.e("MD5", e.getMessage());
			return null;
		}
	}

	public static String getMd5DecrytedString(String input) {
		try {
			byte[] password = input.getBytes("UTF-8");
			byte[] ciphertext = { -68, -112, 66, 78, 85, 50, 22, -63, 16, 24,
					-45, 4, -116, -14, 88, 34, -85, 116, 105, 59, 45, -126 };
			byte[] plaintext = md5Decrypt(password, ciphertext);
			return (new String(plaintext, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] md5Decrypt(byte[] password, byte[] ciphertext) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(password);
			Cipher rc4 = Cipher.getInstance("RC4");
			rc4.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hash, "RC4"));
			return rc4.doFinal(ciphertext);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void showDialogOk(String title, String message,
			Context context) {
		Dialog dlg = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				}).create();
		dlg.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_SEARCH
						&& event.getRepeatCount() == 0) {
					return true;
				}
				if (keyCode == KeyEvent.KEYCODE_MENU) {
					return true;
				}
				return false;
			}

		});
		dlg.show();
	}

	public static void showToast(Context context, String message, int type) {

		Toast.makeText(context, message, type).show();

	}

	public static void showDialogOkWithGoBack(String title, String message,
			final Activity activity) {
		AlertDialog.Builder adb = new AlertDialog.Builder(activity);
		adb.setTitle(title);
		adb.setMessage(message);
	/*	adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				if (BEMenuFragmentActivity.getSharedMenu() != null) {
					BEMenuFragmentActivity.getSharedMenu().onBackPressed();
				} else {
					activity.onBackPressed();
				}

			}
		});*/

		adb.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_SEARCH
						&& event.getRepeatCount() == 0) {
					return true;
				}
				if (keyCode == KeyEvent.KEYCODE_MENU) {
					return true;
				}
				return false;
			}

		});
		AlertDialog ad = adb.create();
		ad.show();
	}

	public static void showToast(String message, Context context) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static String getUSDateTime(String dateString) {
		String formattedDate = "";
		formattedDate = NRUtility.formatDateWithFormats(dateString,
				"yyyy-MM-dd HH:mm:ss", "MM-dd-yyyy hh:mm:ss a");

		return formattedDate;
	}

	public static String formatDate(String dateString) {
		String formattedDate = "";
		if (dateString.contains("/")) {
			formattedDate = NRUtility.formatDateWithFormats(dateString,
					"MM/dd/yyyy hh:mm:ss a", "yyyy-MM-dd HH:mm:ss");
		} else {
			formattedDate = NRUtility.formatDateWithFormats(dateString,
					"MM-dd-yyyy hh:mm:ss a", "yyyy-MM-dd HH:mm:ss");
		}
		return formattedDate;
	}

	public static String get24HourTimeFromDate(String dateString) {
		return (NRUtility.formatDateWithFormats(dateString,
				"yyyy-MM-dd HH:mm:ss", "HH:mm"));
	}

	public static String getTimeFromDate(String dateString) {
		return (NRUtility.formatDateWithFormats(dateString,
				"yyyy-MM-dd HH:mm:ss", "hh:mm a"));
	}

	public static String formatDOB(String DOB) {
		return formatDateWithFormats(DOB, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
	}

	public static String formatMRN(String MRN) {
		return formatDateWithFormats(MRN, "yyyy-MM-dd HH:mm:ss", "MMddyymmss");
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDateWithFormats(String dateString,
			String ipFormat, String opFormat) {
		DateFormat inputFormat = new SimpleDateFormat(ipFormat);
		DateFormat outputFormat = new SimpleDateFormat(opFormat);
		String outputDate = dateString.trim();
		Date date = null;
		try {
			date = inputFormat.parse(dateString.trim());
			outputDate = outputFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("DATE-FORMATE - " + dateString, e.getMessage(), e);
		}
		return outputDate;
	}

	public static void removeDefaults(String key, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(key);
		editor.commit();
	}

	@SuppressLint("SimpleDateFormat")
	public static void setDefaults(String key, String value, Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static Date stringdatetoDate(String date) {
		Date date1 = null;
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date1 = formater.parse(date);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDefaults(String key, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String savedValue = preferences.getString(key, null);
		if (savedValue == null) {
			return null;
		}
		Log.i("savedValue", "" + savedValue);
		return savedValue;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateByAddingDayInterval(int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	public static String getDateWithSecondsInterval(int interval) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, interval);
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	public static Date getDateFromTimeStamp(String timestamp)
			throws ParseException {
		// SimpleDateFormat format = new SimpleDateFormat("MMddyyHHmmss");
		Date date = new Date(Long.parseLong(timestamp));
		// date = new Date(timestamp);
		return date;
	}

	public static String getMonth(int month) {

		switch (month) {
		case 0:
			return "Jan";
		case 1:
			return "Feb";
		case 2:
			return "Mar";
		case 3:
			return "Apr";
		case 4:
			return "May";
		case 5:
			return "Jun";
		case 6:
			return "Jul";
		case 7:
			return "Aug";
		case 8:
			return "Sep";
		case 9:
			return "Oct";
		case 10:
			return "Nov";
		case 11:
			return "Dec";

		}
		return null;
	}

	public static int getDateDifferenceInMinutes(Date olderDate, Date newerDate) {
		Calendar oldCalendar = Calendar.getInstance();
		Calendar newCalendar = Calendar.getInstance();
		oldCalendar.setTime(olderDate);
		newCalendar.setTime(newerDate);

		long milliseconds1 = oldCalendar.getTimeInMillis();
		long milliseconds2 = newCalendar.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (60 * 1000);
		int hours = (int) diffDays;

		return hours;
	}

	@SuppressLint("SimpleDateFormat")
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connec.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected() == true) {
			return true;
		}
		return false;
	}

	public static boolean isDateValid(String date, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			formatter.parse(date);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Downloads the file from a URL.
	 * 
	 * @param fileUrl
	 */
	public static Bitmap downloadFileFromUrl(String fileUrl) {

		URL myFileUrl = null;
		Bitmap imageBitmap = null;

		try {
			myFileUrl = new URL(fileUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			HttpURLConnection connection = (HttpURLConnection) myFileUrl
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream is = connection.getInputStream();
			imageBitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageBitmap;
	}

	public static boolean validateEmail(String email) {

		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher emailMatcher = emailPattern.matcher(email);
		if (!emailMatcher.matches()) {
			return false;
		}

		return true;
	}

	public static ProgressDialog showProgressDialog(Context context) {
		ProgressDialog myProgressDialog = new ProgressDialog(context);
		myProgressDialog.setMessage("Please wait...");
		myProgressDialog.setCancelable(false);
		myProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myProgressDialog.show();
		myProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_SEARCH
						&& event.getRepeatCount() == 0) {
					return true;
				}
				if (keyCode == KeyEvent.KEYCODE_MENU) {
					return true;
				}
				return false;
			}

		});
		return myProgressDialog;
	}

	public static void dismissProgressDialog(ProgressDialog myProgressDialog) {
		if (myProgressDialog != null) {
			try {
				myProgressDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void openPDFFile(File PDFFile) {

		if (PDFFile.exists()) {
			Uri path = Uri.fromFile(PDFFile);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(path, "application/pdf");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			try {
				NRApp.getSharedApplication().startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(NRApp.getSharedApplication(),
						"No Application Available to View PDF",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(NRApp.getSharedApplication(),
					"Unable to open PDF", Toast.LENGTH_SHORT).show();
		}

	}

	public static byte[] readBytes(InputStream inputStream) throws IOException {
		// this dynamically extends to take the bytes you read
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

		// this is storage overwritten on each iteration with bytes
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		// we need to know how may bytes were read to write them to the
		// byteBuffer
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			byteBuffer.write(buffer, 0, len);
		}

		// and then we can return your byte array.
		return byteBuffer.toByteArray();
	}

	public static File getPDFFile(InputStream is, long msgId) {
		String fileName = "Bill_"+msgId+".pdf";
		String filepath = "/mnt/sdcard/";
		if (is != null) {

			Boolean fileIsWritten = false;

			try {
				
				byte[] bytes = readBytes(is);

				FileOutputStream outStream = new FileOutputStream(
						filepath+fileName);

				outStream.write(bytes);

				outStream.close();

				outStream.flush();

				fileIsWritten = true;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (fileIsWritten == true) {
				return new File(filepath+fileName);
			}

		}
		return null;
	}

	@SuppressLint("WorldReadableFiles")
	public static boolean isSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	@SuppressWarnings("deprecation")
	public static SharedPreferences getSharedPreferences() {
		return NRApp.getSharedApplication().getSharedPreferences(
				"myPrefs", Context.MODE_WORLD_READABLE);
	}

	public static int getPixelValue(int value) {
		DisplayMetrics metrics = NRApp.getSharedApplication()
				.getApplicationContext().getResources().getDisplayMetrics();
		return (int) (value * metrics.density);
	}

	public static boolean nullCompare(Object object1, Object object2) {
		if (object1 == null && object2 == null) {
			return true;
		}
		return false;
	}

	public static boolean compare(String string1, String string2) {
		if (nullCompare(string1, string2)) {
			return true;
		} else if (string1 != null && string2 != null) {
			return string1.equalsIgnoreCase(string2);
		}
		return false;
	}

	public static boolean compare(Long value1, Long value2) {
		if (nullCompare(value1, value2)) {
			return true;
		} else if (value1 != null && value2 != null) {
			return value1.compareTo(value2) == 0;
		}
		return false;
	}

	public static boolean compare(Boolean value1, Boolean value2) {
		if (nullCompare(value1, value2)) {
			return true;
		} else if (value1 != null && value2 != null) {
			return value1.compareTo(value2) == 0;
		}
		return false;
	}

}
