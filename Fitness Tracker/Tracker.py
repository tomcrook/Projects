import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from scipy.stats import linregress

weight_df = pd.read_csv('weight tracking.csv')
bench_df = pd.read_csv('bench tracking - Sheet1.csv')

weight_df = weight_df[['Days', 'Weight']]
bench_df = bench_df[['Days', 'Bench']]

bins = np.linspace(0, weight_df['Days'].max(), len(weight_df))

weight_slope, weight_intercept, r_value, p_value, std_err = linregress(weight_df['Days'], weight_df['Weight'])

plt.subplot(2, 1, 1)
plt.plot(bins, weight_intercept + weight_slope*bins, 'r', label='fitted line')
plt.scatter(bins, weight_df['Weight'])

plt.xlabel('Days')
plt.ylabel('Weight')
plt.legend(['Weight Trend (m = ' + str(round(weight_slope, 3)) + ')', 'Weight'])
plt.title('Body Weight')

plt.subplot(2, 1, 2)
bins = np.linspace(0, bench_df['Days'].max(), len(bench_df))

slope, intercept, r_value, p_value, std_err = linregress(bench_df['Days'], bench_df['Bench'])

plt.plot(bins, intercept + slope*bins, 'r', label='fitted line', color='Black')
plt.scatter(bins, bench_df['Bench'])

plt.title('Bench Press')
plt.xlabel('Days')
plt.ylabel('Bench Max')
plt.legend(['Bench Trend (m = ' + str(round(slope, 3)) + ')', 'Weight'])

plt.gcf().set_size_inches((10, 10))
sns.set()
plt.show()

