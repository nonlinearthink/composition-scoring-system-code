import matplotlib.pyplot as plt
from matplotlib import rcParams

def plot_analyse(y_pred,y_val,fig_size=(12,8),title="Actual vs Predicted Score comparision - SkipFlow",save_path=None):
    rcParams['figure.figsize'] = 10, 6

    # plot
    fig = plt.figure(figsize=fig_size)
    plt.title(title)
    plt.ylabel("Score")
    plt.plot(y_pred, color='red', label='Predicted', linestyle='dashed')
    plt.plot(y_val, color='blue', label='Original', linestyle='solid')
    plt.legend()
    if save_path:
        plt.savefig(save_path)
    plt.show()