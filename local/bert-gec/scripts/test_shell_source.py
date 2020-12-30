import os
import time
import subprocess

cmd = "bash test_shell.sh"
# apply=os.popen(cmd)  # cant wait
# subprocess.call(cmd,shell=True)
child=subprocess.Popen(cmd,shell=True)
child.wait()
# time.sleep(3)
print("this is source:")