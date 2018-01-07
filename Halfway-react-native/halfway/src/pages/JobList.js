import React from 'react';
import { ToolbarAndroid, View, ListView, StyleSheet, Text, TouchableOpacity, StatusBar, Button } from 'react-native';
import { data } from '../../demoData';

import ListItem from '../components/ListItem';

let ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });
const Realm = require('realm');

export default class JobList extends React.Component {
  static navigationOptions = {
    title: 'HalfWay',
    headerStyle: { marginTop: 24 }
  };
  constructor(props) {
    super(props);

    this.state = {
      dataSource: ds.cloneWithRows(data),
      newJob: this.props.navigation.state,
      realm: null
    };
  }

  componentWillMount() {
    Realm.open({
      schema: [{ name: 'Jobs', properties: { name: 'string', description: 'string', date: 'string', employer: 'string' } }]
    }).then(realm => {
      realm.write(() => {
        realm.create('Jobs', { name: 'string', description: 'string', date: 'string', employer: 'string' });
      });
      this.setState({ realm });
    });
  }

  refreshList() {
    var newDs = [];
    newDs = data;
    console.log(this.state.newJob);
    newDs[this.state.newJob.id] = this.state.newJob;
    //console.log(newDs);
    this.setState({
      dataSource: ds.cloneWithRows(newDs)
    })
    console.log(this.state);
  }

  render() {
    const info = this.state.realm.objects('Jobs');
    console.log(info);
    const { navigate } = this.props.navigation;
    return (
      <View>
        <Button title="Refresh" onPress={() => this.refreshList()} />
        <ListView
          dataSource={this.state.dataSource}
          renderRow={(rowData, sectionID, rowID) =>
            <View>
              <ListItem
                rowData={rowData}
              />
              <Button color="lightslategrey" title="Details" onPress={() => navigate('Details', { rowData })} />
            </View>
          }
        />
      </View>
    );
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
  body: {
    marginTop: 20
  }
});
