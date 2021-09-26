package com.example.drivefor2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model model)
    {
       holder.type.setText(model.getType());
       holder.date.setText(model.getDate());
       holder.message.setText(model.getMessage());
       Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

                    holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                    .setExpanded(true,1100)
                                    .create();

                            View myview=dialogPlus.getHolderView();
                            final EditText purl=myview.findViewById(R.id.uimgurl);
                            final EditText type=myview.findViewById(R.id.utype);
                            final EditText date=myview.findViewById(R.id.udate);
                            final EditText message=myview.findViewById(R.id.umessage);
                            Button submit=myview.findViewById(R.id.usubmit);

                            purl.setText(model.getPurl());
                            type.setText(model.getType());
                            date.setText(model.getDate());
                            message.setText(model.getMessage());

                            dialogPlus.show();

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("purl",purl.getText().toString());
                                        map.put("type",type.getText().toString());
                                        map.put("date",date.getText().toString());
                                        map.put("message",message.getText().toString());

                                        FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("peoples")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                        Toast.makeText(holder.type.getContext(), "Reminder Updated Successfully!.", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                        Toast.makeText(holder.type.getContext(), "Error while Updating!.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });


                        }
                    });


                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                            builder.setTitle("Delete Panel");
                            builder.setMessage("Do You want to Delete this reminder...?");


                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("peoples")
                                            .child(getRef(position).getKey()).removeValue();
                                    Toast.makeText(holder.type.getContext(), "Deleted reminder can't undo!!!.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(holder.type.getContext(), "Deleting Canceled.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            builder.show();
                        }
                    });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
       return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView type,date,message;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            type=(TextView)itemView.findViewById(R.id.typetext);
            date=(TextView)itemView.findViewById(R.id.datetext);
            message=(TextView)itemView.findViewById(R.id.messagetext);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
