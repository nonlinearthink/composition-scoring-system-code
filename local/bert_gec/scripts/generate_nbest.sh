beam=5
bert_type=bert-base-cased
SUBWORD_NMT=./bert_gec/subword/subword_nmt
FAIRSEQ_DIR=./bert_gec/bert-nmt
BPE_MODEL_DIR=../gec-pseudodata/bpe
MODEL_DIR=../bert-fuse
OUTPUT_DIR=$MODEL_DIR/output
PREPROCESS=../bert-fuse

echo Generating...
CUDA_VISIBLE_DEVICES=$gpu python -u ${FAIRSEQ_DIR}/interactive.py $PREPROCESS -s src -t trg < $OUTPUT_DIR/test.cat.src > $OUTPUT_DIR/test.nbest.tok