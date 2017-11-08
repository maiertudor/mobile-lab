import React from 'react';
import { View, StyleSheet, Text, StatusBar } from 'react-native';

import Login from './src/pages/Login';

export default class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <View style={styles.container}>
        <StatusBar barStyle="light-content" style={styles.toolbar} />
        <Login/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#77bbe2',
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },
  mainText: {
    color: "#fff",
    fontSize: 18
  },
  toolbar: {
    backgroundColor: "#02579B",
  }

});
