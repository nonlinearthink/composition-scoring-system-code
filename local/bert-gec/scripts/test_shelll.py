import os
import subprocess

# cmd = "python -u ../bert-nmt/interactive.py $PREPROCESS -s src -t trg < $OUTPUT_DIR/$id.src > $OUTPUT_DIR2/$id.tok"
# activate = "conda activate torch1.1"
# apply=os.popen(activate)
cmd = "bash generate.sh 001"
child=subprocess.Popen(cmd,shell=True)
child.wait()

print("finall")
wait=True