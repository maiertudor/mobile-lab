import React from 'react';
import { ToolbarAndroid, View, ListView, StyleSheet, Text, TouchableOpacity, StatusBar } from 'react-native';
import { Row } from './Row';
import { data } from './demoData';

const ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });

export default class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      dataSource: ds.cloneWithRows([
        {
          "id": "0",
          "name": "Janitor",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
        {
          "id": "1",
          "name": "Java developer",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
        {
          "id": "2",
          "name": "Babysister",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
        {
          "id": "3",
          "name": "Garbageman",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
        {
          "id": "4",
          "name": "Steward",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
        {
          "id": "5",
          "name": "Crosetar",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
        {
          "id": "6",
          "name": "Dev ops",
          "descripton": "simple tasks",
          "date": "20/02/2017",
          "employer": "Emerson"
        },
      ]),
    };
  }

  _renderRow(rowData) {
    return (
      <TouchableOpacity>
        <View style={styles.container}>
          <Text style={styles.textLeft}>{rowData.name}</Text>
          <Text style={styles.textLeft}>{rowData.descripton}</Text>
          <Text style={styles.textRight}>{rowData.date}</Text>
          <Text style={styles.textRight}>{rowData.employer}</Text>
        </View >
      </TouchableOpacity>
    );
  }
  render() {
    return (
      <View>
        <StatusBar />
        <View style={styles.toolbar}>
          <View style={styles.toolbarContent}>
            <Text style={styles.toolbarText}>HalfWay</Text>
          </View>

        </View>
        <ListView
          dataSource={this.state.dataSource}
          renderRow={this._renderRow} />
      </View>
    );
  }
  openJob = (job) => {
    alert("Selected job: " + job.name)
  }
}

const styles = StyleSheet.create({
  container: {
    padding: 10,
    margin: 5,
    backgroundColor: '#ECEFF1',
    borderRadius: 2,
    borderWidth: 1,
    borderColor: "#E0E0E0"
  },
  textLeft: {
    color: '#263238',
    textAlign: "left",
    fontSize: 18
  },
  textRight: {
    color: '#263238',
    textAlign: "right"
  },
  body: {
    marginTop: 20
  },
  toolbar: {
    height: 70,
    backgroundColor: "#01579B",
    flex: 0,
    flexDirection: "row",
    alignItems: "flex-end"
  },
  toolbarContent: {
    flex:1,
    alignItems: "center"
  },
  toolbarText: {
    color: "#fff",
    fontSize: 20,
    margin: 10,
    fontWeight: "bold"
  }
});
