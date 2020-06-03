package sk.upjs.micma.epdat_client_app.adapters;

import android.graphics.Color;
import android.view.MotionEvent;
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
        recordIdTextView.setText("ID: "+record.getId());
        boolean end = false;
        if (record.getEndopolyploidy()==1) end = true;
        endopolyploidyTextView.setText("Endopolyploidy: "+end);
        String chrom = "Chrom. num.: ";
        if (record.getChromosomeNumber()!=null && !record.getChromosomeNumber().equals("")) {chrom+=record.getChromosomeNumber();} else {chrom+="-";}
        chromosomeNumberTextView.setText(chrom);
        String ploid = "Ploidy level: ";
        if (record.getPloidyLevel()!=-1 && record.getPloidyLevel()!=0) {ploid+=record.getPloidyLevel()+"x";} else {ploid+="-";}
        ploidyLevelTextView.setText(ploid);
        String numb = " ";
        if (record.getNumber()!=-1 && record.getNumber()!=-0) {numb+=record.getNumber();} else {numb+="-";}
        numberTextView.setText(numb);
        String itype = "Index type: ";
        if(record.getIndexType()!=null && !record.getIndexType().equals("")) {itype+=record.getIndexType();} else {itype+="-";}
        indexTypeTextView.setText(itype);
        tissueTextView.setText("Tissue: "+record.getTissue());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordOnClickListener.onRecordClick(record);
            }
        });
    }
}
