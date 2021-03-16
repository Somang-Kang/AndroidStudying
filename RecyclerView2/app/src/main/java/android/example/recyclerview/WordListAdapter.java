package android.example.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private LayoutInflater mInflater;
    private final LinkedList<String> mWordList;

    public WordListAdapter(Context context, LinkedList<String> wordList){
        mInflater = LayoutInflater.from(context);
        this.mWordList=wordList;
    }

    //아이템뷰를 저장하는 클래스
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView,WordListAdapter adapter){
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter=adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = mWordList.get(mPosition);
            mWordList.set(mPosition,"Clicked! "+element);
            mAdapter.notifyDataSetChanged();
        }
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item,parent,false);
        return new WordViewHolder(mItemView,this);
    }


    //position에 해당하는 data를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(WordViewHolder holder,int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    //전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
