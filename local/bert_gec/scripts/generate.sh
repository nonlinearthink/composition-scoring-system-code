#!/bin/sh
# >>> conda initialize >>>
# !! Contents within this block are managed by 'conda init' !!
__conda_setup="$('/home/wyt/anaconda3/bin/conda' 'shell.bash' 'hook' 2> /dev/null)"
if [ $? -eq 0 ]; then
    eval "$__conda_setup"
else
    if [ -f "/home/wyt/anaconda3/etc/profile.d/conda.sh" ]; then
        . "/home/wyt/anaconda3/etc/profile.d/conda.sh"
    else
        export PATH="/home/wyt/anaconda3/bin:$PATH"
    fi
fi
unset __conda_setup
# <<< conda initialize <<<

conda activate essay
# change the env activate command above according to your own need

#id=$1
#beam=5
#FAIRSEQ_DIR=./bert_gec/bert-nmt
#MODEL_DIR=./bert_gec/cache_file
#OUTPUT_DIR=$MODEL_DIR/test_cat
#OUTPUT_DIR2=$MODEL_DIR/nbest_tok
#OUTPUT_DIR3=$MODEL_DIR/best_tok
#PREPROCESS=./bert_gec/bert-fuse

id=$1
beam=5
FAIRSEQ_DIR=../bert-nmt
MODEL_DIR=../cache_file
OUTPUT_DIR=$MODEL_DIR/test_cat
OUTPUT_DIR2=$MODEL_DIR/nbest_tok
OUTPUT_DIR3=$MODEL_DIR/best_tok
PREPROCESS=../bert-fuse
# change the file path above according to your own need

echo Generating...
python -u ${FAIRSEQ_DIR}/interactive.py $PREPROCESS -s src -t trg --cpu < $OUTPUT_DIR/$id.src > $OUTPUT_DIR2/$id.tok

cat $OUTPUT_DIR2/$id.tok | grep "^H"  | python -c "import sys; x = sys.stdin.readlines(); x = ' '.join([ x[i] for i in range(len(x)) if (i % ${beam} == 0) ]); print(x)" | cut -f3 > $OUTPUT_DIR3/$id.tok
sed -i '$d' $OUTPUT_DIR3/$id.tok
