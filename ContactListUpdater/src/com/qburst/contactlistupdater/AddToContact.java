package com.qburst.contactlistupdater;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class AddToContact extends Activity implements OnClickListener {

	private static final int CONTACT_PICKER_RESULT = 123;
	int getPosition;
	int rawContactId;
	String fileName;
	String name;
	String id;
	Bitmap bitmap;
	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	byte[] b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_grid_pic);

		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		Button contactList = (Button) findViewById(R.id.contact_list);
		Button facebook = (Button) findViewById(R.id.facebook);

		contactList.setOnClickListener(this);
		facebook.setOnClickListener(this);

		getPosition = getIntent().getExtras().getInt("position");
		Log.d("position", "" + getPosition);

		String ExternalStorageDirectoryPath = Environment
				.getExternalStorageDirectory().getAbsolutePath();

		String targetPath = ExternalStorageDirectoryPath + "/faces";

		File targetDirector = new File(targetPath);

		File[] files = targetDirector.listFiles();

		for (int i = 0; i < files.length; i++) {

			if (i == getPosition) {
				File file = files[i];
				fileName = file.getPath();
				Log.d("fileName", fileName);
				break;
			}
		}

		bitmap = BitmapFactory.decodeFile(fileName);
		imageView.setImageBitmap(bitmap);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.process_grid_pic, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.contact_list) {
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
					Contacts.CONTENT_URI);
			startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
		}

		if (v.getId() == R.id.facebook) {
			Intent callFacebookActivity = new Intent(this,
					FacebookActivity.class);
			callFacebookActivity.putExtra("imagePath", fileName);
			startActivity(callFacebookActivity);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CONTACT_PICKER_RESULT:
				// handle contact results
				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst()) {
					name = c.getString(c
							.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					id = c.getString(c.getColumnIndex(BaseColumns._ID));
					// TODO Whatever you want to do with the selected contact
					// name.
				}
				// System.out.println("Contact = " + data);
				addContactImage(name, id);
				break;
			}
		} else {
			// gracefully handle failure
			Log.d("Warning", "Warning: activity result not ok");
		}
	}

	private void addContactImage(String userName, String userId) {

		// TODO Auto-generated method stub
		Log.d(userName, userName);

		ContentResolver cr = getContentResolver();
		Cursor cur = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur.getColumnIndex(BaseColumns._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				System.out.println(name);

				rawContactId = cur.getInt(cur.getColumnIndex(BaseColumns._ID));
				System.out.println("RawContactId = " + rawContactId);

				if (name.equals(userName)) {

					int picId = cur
							.getInt(cur
									.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
					Log.d("Pic Id", "" + picId);

					// retrieve contact pic to be added
					Bitmap bmImage = BitmapFactory.decodeFile(fileName
							.toString());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bmImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
					byte[] b = baos.toByteArray();

					Cursor cursor = cr.query(
							ContactsContract.RawContacts.CONTENT_URI, null,
							ContactsContract.RawContacts.CONTACT_ID + "=?",
							new String[] { id }, null);

					if (cursor.moveToFirst()) {
						rawContactId = cursor.getInt(cursor
								.getColumnIndex(BaseColumns._ID));

						int idIdx = cursor
								.getColumnIndexOrThrow(ContactsContract.Data._ID);

						int photoRow = cursor.getInt(idIdx);

						if (rawContactId > -1) {

							if (picId == 0) {
								// Insert new photo
								Log.d("PicID", "PicID is 0");
								ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
								ops.add(ContentProviderOperation
										.newInsert(
												ContactsContract.Data.CONTENT_URI)
										.withValue(
												ContactsContract.Data.RAW_CONTACT_ID,
												rawContactId)
										.withValue(
												ContactsContract.Data.MIMETYPE,
												ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
										.withValue(
												ContactsContract.CommonDataKinds.Photo.PHOTO,
												b).build());
								try {
									cr.applyBatch(ContactsContract.AUTHORITY,
											ops);
								} catch (RemoteException e) {

								} catch (OperationApplicationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (picId != 0) {
								// Update current photo
								Log.d("PicID", "PicID is not equal to 0");

								ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
								ops.add(ContentProviderOperation
										.newUpdate(
												ContactsContract.Data.CONTENT_URI)
										.withSelection(
												ContactsContract.Data._ID
														+ " =?",
												new String[] { Integer
														.toString(photoRow) })
										.withValue(
												ContactsContract.Data.RAW_CONTACT_ID,
												id)
										.withValue(
												ContactsContract.Data.IS_SUPER_PRIMARY,
												1)
										.withValue(
												ContactsContract.Data.MIMETYPE,
												ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
										.withValue(
												ContactsContract.Data.DATA15, b)
										.build());

								try {
									cr.applyBatch(ContactsContract.AUTHORITY,
											ops);
								} catch (RemoteException e) {

								} catch (OperationApplicationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					}

					return;
				}

			}

		}
	}
}
