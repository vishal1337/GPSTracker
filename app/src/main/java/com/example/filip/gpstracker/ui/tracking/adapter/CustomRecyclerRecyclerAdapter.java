package com.example.filip.gpstracker.ui.tracking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.filip.gpstracker.ItemListener;
import com.example.filip.gpstracker.R;
import com.example.filip.gpstracker.model.Session;
import com.example.filip.gpstracker.ui.tracking.presenter.SessionsFragmentAdapterPresenter;
import com.example.filip.gpstracker.ui.tracking.presenter.SessionsAdapterPresenterImpl;

import java.util.ArrayList;

/**
 * Created by Filip on 11/03/2016.
 */
public class CustomRecyclerRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerRecyclerAdapter.ViewHolder> implements CustomRecyclerAdapterView {
    private final ArrayList<Session> mListOfSessions = new ArrayList<>();
    private final SessionsFragmentAdapterPresenter presenter;
    private final ItemListener listener;

    public CustomRecyclerRecyclerAdapter(String username, ItemListener listener) {
        this.presenter = new SessionsAdapterPresenterImpl(this, username);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sessions_fragment_session_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String sessionName = mListOfSessions.get(position).getSessionName();
        holder.mSessionTextView.setText(sessionName);
    }

    @Override
    public int getItemCount() {
        return mListOfSessions.size();
    }

    @Override
    public void addAllItems(ArrayList<Session> sessionsList) {
        mListOfSessions.clear();
        mListOfSessions.addAll(sessionsList);
        notifyDataSetChanged();
    }

    @Override
    public void fillAdapterWithItems() {
        presenter.requestListOfSessions();
    }

    @Override
    public void deleteItem(String sessionName) {
        presenter.sendDeleteRequest(sessionName); //deletes it from the firebase as well
        presenter.requestListOfSessions(); //refreshes the list after deletion
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnLongClickListener, RecyclerView.OnClickListener {
        private TextView mSessionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mSessionTextView = (TextView) itemView.findViewById(R.id.sessions_list_item_text_view);
            mSessionTextView.setOnClickListener(this);
            mSessionTextView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mSessionTextView) {
                String sessionName = mSessionTextView.getText().toString();
                listener.onItemClick(sessionName);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v == mSessionTextView) {
                String sessionName = mSessionTextView.getText().toString();
                listener.onLongItemClick(sessionName);
            }
            return true;
        }
    }
}
