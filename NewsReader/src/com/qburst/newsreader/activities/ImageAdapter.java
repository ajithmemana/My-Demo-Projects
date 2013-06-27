package com.qburst.newsreader.activities;

import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qburst.newsreader.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends ArrayAdapter<Results> {

	    int resource;
	    String response;
	    Context context;
	    private LayoutInflater mInflater;
	    private List<Results> objects;

	    final static String Title = "Title";
	    final static String NewsList = "NewsList";
	    	   
	    private ImageLoader imageLoader= ImageLoader.getInstance();
		    
		String[] imageUrls;



	public ImageAdapter (Context context, int resource,
			List<Results> objects) {
		
		super(context, resource, objects);
		this.resource = resource;
        mInflater = LayoutInflater.from(context);
        this.objects = objects;
        
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(objects==null) {
			return 0;
		}
		return objects.size();
	//	return 9 ;
	}
	
	@Override
	public Results getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	static class ViewHolder {
		
	        TextView Title;
	        ImageView Image;
	        
	     	    }
	
	    // create a new ImageView for each item referenced by the Adapter
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	
        Results lm = getItem(position);
        ViewHolder holder=new ViewHolder();
                      
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.grid_content, null);
			
            holder.Title = (TextView) convertView.findViewById(R.id.icon_text);
            holder.Image = (ImageView) convertView.findViewById(R.id.icon_image);
        
 
            convertView.setTag(holder);
			
		}
		else
		{
			
			holder = (ViewHolder) convertView.getTag();
			
			
		}
	
		if(null !=  lm && null != lm.getWebTitle())
		{
			
			holder.Title.setText(lm.getWebTitle());
			Log.d("WEBTITLE", lm.getWebTitle());
			
			if(null != lm.getFields().getThumbnail())
			{
			String thumb =lm.getFields().getThumbnail();
			imageLoader.displayImage(thumb, holder.Image);
			}
			else
			holder.Image.setImageResource(R.drawable.image);
				
		}
		
		
		Log.d("title", lm.getWebTitle());
     		
		return convertView;
    }



	public List<Results> getObjects() {
		return objects;
	}



	public void setObjects(List<Results> objects) {
		this.objects = objects;
	}

   
}