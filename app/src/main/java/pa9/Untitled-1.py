def construct():
  """Returns a tuple consisting of:
    row descriptors,
    column descriptors,
    and data
    from the example"""
  row_names = ['arthritis', 'no arthritis']
  col_names = ['elite', 'non-elite', 'did not play']
  data = [[10, 9, 24], [61, 206, 548]]
  return (row_names, col_names, data)

def row_marginal(rows, data):
  """Computes the marginal distribution for all row-values
  returned as a Dictionary<String, Integer>"""
  # TODO
  result = {}
  for i in range(len(rows)):
    result[rows[i]] = sum(data[i])
  return result

def col_marginal(cols, data):
  """Computes the marginal distribution for all column-values
  returned as a Dictionary<String, Integer>"""
  # TODO
  result = {}
  for j in range(len(cols)):
    result[cols[j]] = sum(row[j] for row in data)
  return result

def compute_total(data):
  """Computes the total of all values in the data table."""
  # TODO
  return sum(sum(row) for row in data)

def joint_prob(rows, cols, data, row_var, col_var):
  """Computes a joint probability for a given combination of row value and column value."""
  # TODO
  row_idx = rows.index(row_var)
  col_idx = cols.index(col_var)
  return data[row_idx][col_idx] / compute_total(data)

def table_to_string(rows, cols, data, r_marg, col_marg, total):
  """Constructs a string representation of a two-way table in a semi-reasonable structure."""
  # TODO
  result = " " * 12
  for col in cols:
    result += f"{col:>10} "
  result += "    TOTAL\n"

  for i in range(len(rows)):
    result += f"{rows[i]:<12}"
    for j in range(len(cols)):
      result += f"{data[i][j]:>10} "
    result += f"{r_marg[rows[i]]:>8}\n"

  result += "TOTAL".ljust(12)
  for col in cols:
    result += f"{col_marg[col]:>10} "
  result += f"{total:>8}"
  return result

def main():
  rows, cols, data = construct()
  assert joint_prob(rows, cols, data, 'arthritis', 'elite') == 10 / 858
  assert joint_prob(rows, cols, data, 'no arthritis', 'did not play') == 548 / 858
  assert row_marginal(rows, data)['arthritis'] == 43
  assert row_marginal(rows, data)['no arthritis'] == 815
  assert col_marginal(cols, data)['elite'] == 71
  assert col_marginal(cols, data)['non-elite'] == 215
  assert col_marginal(cols, data)['did not play'] == 572
  print(table_to_string(rows, cols, data, row_marginal(rows, data),
                        col_marginal(cols, data), compute_total(data)))
  print('done')

if __name__ == "__main__":
  main()