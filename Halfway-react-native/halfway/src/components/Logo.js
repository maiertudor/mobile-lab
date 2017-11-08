import React from 'react';
import { View, StyleSheet, Text, StatusBar, Image } from 'react-native';

export default class Logo extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <View style={styles.container}>
                <Image style={styles.logo}
                    source={require('../../assets/logo_halfway.png')} />
                <Text style={styles.logoText}> Welcome to HalfWay App</Text>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    logoText: {
        fontSize: 18,
        color: 'rgba(255,255,255,0.8)'
    },
    logo: {
        width: 150,
        height: 150
    }
});
