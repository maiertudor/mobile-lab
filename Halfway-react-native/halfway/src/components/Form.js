import React from 'react';
import { View, StyleSheet, Text, StatusBar, TextInput, TouchableOpacity } from 'react-native';

export default class Form extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <View style={styles.container}>
                <TextInput style={styles.inputBox}
                    underlineColorAndroid='rgba(0,0,0,0)'
                    placeholder="Username"
                    placeholderTextColor="rgba(255,255,255,0.5)" />

                <TextInput style={styles.inputBox}
                    underlineColorAndroid='rgba(0,0,0,0)'
                    placeholder="Password"
                    secureTextEntry={true}
                    placeholderTextColor="rgba(255,255,255,0.5)" />

                <TouchableOpacity style={styles.button}>
                    <Text style={styles.buttonText}>Login</Text>
                </TouchableOpacity>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        alignItems: 'center',
        justifyContent: 'center'

    },
    inputBox: {
        width: 300,
        backgroundColor: 'rgba(0,0,0,0.1)',
        borderRadius: 20,
        paddingHorizontal: 15,
        paddingVertical: 10,
        fontSize: 16,
        color: "#fff",
        marginVertical: 10
    },
    buttonText: {
        fontSize: 18,
        fontWeight: '500',
        color: '#fff',
        textAlign: "center"
    },
    button: {
        width:300,
        backgroundColor: 'rgba(0,0,0,0.4)',
        borderRadius: 25,
        marginVertical: 10,
        paddingVertical: 12
    }
});
