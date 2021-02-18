import pandas as pd
import plotly.express as px

players_df = pd.read_csv('swindon town - sept 2030 - Sheet1.csv')

print(players_df.columns)

advanced_forward = ['Cro', 'Dri', 'Fin', 'Hea', 'Ant', 'Cmp', 'Fla', 'OtB', 'Wor', 'Pac']
winger = ['Cro', 'Dri', 'Tec', 'Dec', 'Fla', 'OtB', 'Acc', 'Agi', 'Bal', 'Pac']
deep_lying_playmaker_s = ['Pas', 'Tck', 'Tec', 'Cmp', 'Dec', 'Pos', 'Tea', 'Str', 'Fir', 'OtB']
deep_lying_playmaker_d = ['Pas', 'Tck', 'Tec', 'Cmp', 'Dec', 'Pos', 'Tea', 'Str', 'Mar']


def get_player_averages_by_position(df, pos):
    pos = ['Age', 'Name'] + pos
    position_df = df[pos]
    position_df['avg'] = position_df.loc[:, pos[2]:].astype(float).mean(axis=1)
    position_df = position_df.sort_values('avg', ascending=False)
    return position_df


def get_player_averages_by_position_column(df, pos):
    df = get_player_averages_by_position(df, pos)
    return df.loc[:, pos[2]:].astype(float).mean(axis=1)


players_df['dlp_s avg'] = get_player_averages_by_position_column(players_df, deep_lying_playmaker_d)
players_df['af avg'] = get_player_averages_by_position_column(players_df, advanced_forward)

print(players_df)