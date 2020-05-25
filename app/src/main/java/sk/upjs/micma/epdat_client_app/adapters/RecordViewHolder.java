package sk.upjs.micma.epdat_client_app.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.RecordOnClickListener;
import sk.upjs.micma.epdat_client_app.models.Record;

class RecordViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private TextView recordIdTextView;
    private TextView endopolyploidyTextView;
    private TextView chromosomeNumberTextView;
    private TextView ploidyLevelTextView;
    private TextView numberTextView;
    private TextView indexTypeTextView;
    private TextView tissueTextView;


    public RecordViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        recordIdTextView = itemView.findViewById(R.id.RecordIdTextView);
        endopolyploidyTextView = itemView.findViewById(R.id.EndopolyploidyTextView);
        chromosomeNumberTextView = itemView.findViewById(R.id.ChromosomeNumberTextView);
        ploidyLevelTextView =  itemView.findViewById(R.id.PloidyLevelTextView);
        numberTextView =  itemView.findViewById(R.id.NumberTextView);
        indexTypeTextView = itemView.findViewById(R.id.IndexTypeTextView);
        tissueTextView = itemView.findViewById(R.id.TissueTextView);
    }

    public void bind(Record record, RecordOnClickListener recordOnClickListener){
        recordIdTextView.setText(""+record.getId());
        endopolyploidyTextView.setText(""+record.getEndopolyploidy());
        chromosomeNumberTextView.setText(""+record.getChromosomeNumber());
        ploidyLevelTextView.setText(""+record.getPloidyLevel());
        numberTextView.setText(""+record.getNumber());
        indexTypeTextView.setText(""+record.getIndexType());
        tissueTextView.setText(""+record.getTissue());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordOnClickListener.onRecordClick(record);
            }
        });
    }
}
