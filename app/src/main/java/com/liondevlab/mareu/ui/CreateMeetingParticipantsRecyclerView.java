package com.liondevlab.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liondevlab.mareu.R;

import java.util.List;

/**
 * MaRéu
 * Created by LioNDeVLaB on 03/02/2021
 */
@SuppressWarnings("FieldMayBeFinal")
public class CreateMeetingParticipantsRecyclerView extends RecyclerView.Adapter<CreateMeetingParticipantsRecyclerView.ViewHolder> {

	private List<String> mParticipantsList;
	private CreateMeetingParticipantsRecyclerInterface mCreateMeetingParticipantsRecyclerInterface;
	CreateMeetingParticipantsRecyclerView(List<String> items, CreateMeetingParticipantsRecyclerInterface pCreateMeetingParticipantsRecyclerInterface) {
		mParticipantsList = items;
		mCreateMeetingParticipantsRecyclerInterface = pCreateMeetingParticipantsRecyclerInterface;
	}


	@NonNull
	@Override
	public CreateMeetingParticipantsRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_participants_added, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CreateMeetingParticipantsRecyclerView.ViewHolder holder, int position) {
		final String participant = mParticipantsList.get(position);
		holder.mItemParticipantName.setText(holder.itemView.getContext().getString(R.string.create_meeting_participant_added_name, participant));
		holder.mItemDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCreateMeetingParticipantsRecyclerInterface.deleteParticipantFromList(participant);
			}
		});
	}


	@Override
	public int getItemCount() {
		return mParticipantsList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		//ImageView mItemParticipantAvatar;
		TextView mItemParticipantName;
		ImageButton mItemDeleteButton;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			//mItemParticipantAvatar = itemView.findViewById(R.id.item_participant_added_avatar);
			mItemParticipantName = itemView.findViewById(R.id.item_participant_added_name);
			mItemDeleteButton = itemView.findViewById(R.id.item_participant_added_delete_button);
		}
	}
}
