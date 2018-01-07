import React from 'react';
import { View, StyleSheet, Text, StatusBar, AppRegistry } from 'react-native';

import Login from './src/pages/Login';
import JobList from './src/pages/JobList';
import Toolbar from './src/components/Toolbar';
import JobDetails from './src/pages/JobDetails';

import { StackNavigator } from 'react-navigation';

export const RootNavigator = StackNavigator({
    Home: {
        screen: JobList
    },
    Details: {
        screen: JobDetails
    },
});

AppRegistry.registerComponent('RootNavigator', () => RootNavigator);

export default class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <RootNavigator style={styles.mainContent}/>
    );
  }
}

const styles = StyleSheet.create({
  mainContent: {
    marginTop: 20,
    padding: 20
  },
  container: {
    backgroundColor: '#77bbe2'
  },
  mainText: {
    color: "#fff",
    fontSize: 18
  },
  toolbar: {
    backgroundColor: "#02579B",
  }

});